package net.example.postaltrackingservice.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.exception.EventRegistrationException;
import net.example.postaltrackingservice.exception.PostageStatusNotFoundException;
import net.example.postaltrackingservice.handler.EntityPresenceHandler;
import net.example.postaltrackingservice.mapper.PostageEventMapper;
import net.example.postaltrackingservice.model.entity.PostageStatus;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.model.enums.EventType;
import net.example.postaltrackingservice.model.dto.PostageEventCreateDto;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.repository.PostageEventRepository;
import net.example.postaltrackingservice.repository.PostageStatusRepository;
import net.example.postaltrackingservice.service.PostageEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static net.example.postaltrackingservice.model.enums.EventType.*;
import static net.example.postaltrackingservice.model.enums.PostalItemStatus.*;

@Service
@RequiredArgsConstructor
public class PostageEventServiceImpl implements PostageEventService {

    private final PostageEventRepository postageEventRepository;
    private final PostageStatusRepository postageStatusRepository;
    private final PostageEventMapper postageEventMapper;
    private final EntityPresenceHandler entityPresenceHandler;

    @Transactional
    @Override
    public PostageEventReadDto createPostageEvent(@NotNull PostageEventCreateDto createDto, @NotNull EventType eventType) {

        checkEntityPresence(createDto);

        if (eventType == ARRIVAL) {
            return createArrivalEvent(createDto);
        } else if (eventType == DEPARTURE) {
            return createDepartureEvent(createDto);
        } else {
            throw new EventRegistrationException(
                    String.format("Указано неподдерживаемое почтовое событие '%s'", eventType));
        }

    }

    @Transactional
    @Override
    public void receivePostalItemByAddressee(@NotNull PostageEventCreateDto createDto) {

        checkEntityPresence(createDto);
        PostageStatus status = getPostalItemStatusForUpdateOrThrowException(createDto.postalItemID());

        if (status.getPostalItemStatus() == IN_POST_OFFICE
                && postalItemInPostOffice(createDto)) {
            createEvent(createDto, DEPARTURE);
            updatePostageStatus(status, RECEIVED_BY_ADDRESSEE);
        } else {
            throw new EventRegistrationException(
                    String.format("Почтовое отправление '%s' не найдено в почтовом отделении '%s'",
                            createDto.postalItemID(), createDto.postOfficeCode()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostageEventReadDto> findAllEventsByPostalItemID(long postalItemID) {
        return postageEventRepository.findAllByPostalItemOrderByEventDate(postalItemID).stream()
                .map(postageEventMapper::mapToReadDto)
                .collect(toList());
    }

    private PostageEventReadDto createArrivalEvent(PostageEventCreateDto createDto) {

        PostageStatus status = getPostalItemStatusForUpdateOrThrowException(createDto.postalItemID());

        PostageEventReadDto readDto;
        if (status.getPostalItemStatus() == REGISTERED
                || status.getPostalItemStatus() == ON_WAY) {
            readDto = createEvent(createDto, EventType.ARRIVAL);
            updatePostageStatus(status, IN_POST_OFFICE);
        } else {
            throw new EventRegistrationException(
                    String.format("Регистрация прибытия почтового отправления доступно со статусами: REGISTERED, ON_WAY. Текущий статус '%s'", status));
        }

        return readDto;
    }

    private PostageEventReadDto createDepartureEvent(PostageEventCreateDto createDto) {

        PostageStatus status = getPostalItemStatusForUpdateOrThrowException(createDto.postalItemID());

        PostageEventReadDto readDto;
        if (status.getPostalItemStatus() == IN_POST_OFFICE
                && postalItemInPostOffice(createDto)) {
            readDto = createEvent(createDto, DEPARTURE);
            updatePostageStatus(status, ON_WAY);
        } else {
            throw new EventRegistrationException(
                    String.format("Почтовое отправление '%s' не найдено в почтовом отделении '%s'",
                            createDto.postalItemID(), createDto.postOfficeCode()));
        }

        return readDto;
    }

    private PostageStatus getPostalItemStatusForUpdateOrThrowException(Long postalItemID) {

        return postageStatusRepository.getPostageStatusForUpdate(postalItemID)
                .orElseThrow(() -> new PostageStatusNotFoundException(
                        String.format("Не найден статус почтового отправления '%s'", postalItemID)));

    }

    private boolean postalItemInPostOffice(PostageEventCreateDto createDto) {

        return postageEventRepository.findLastEventByPostalItem(createDto.postalItemID())
                .map(lastEvent -> createDto.postOfficeCode().equals(lastEvent.getPostOffice().getIndex()))
                .orElse(false);
    }

    private void checkEntityPresence(PostageEventCreateDto postageEventCreateDto) {
        entityPresenceHandler.checkPostOfficeByIndex(postageEventCreateDto.postOfficeCode());
        entityPresenceHandler.checkPostalItemByID(postageEventCreateDto.postalItemID());
    }

    private PostageEventReadDto createEvent(PostageEventCreateDto postageEventCreateDto,
                                            EventType eventType) {
        return Optional.of(postageEventCreateDto)
                .map(postageEventMapper::map)
                .map(postageEvent -> {
                    postageEvent.setEventType(eventType);
                    postageEvent.setEventDate(OffsetDateTime.now(Clock.systemUTC()));
                    return postageEventRepository.saveAndFlush(postageEvent);
                })
                .map(postageEventMapper::mapToReadDto)
                .orElseThrow();
    }

    private void updatePostageStatus(PostageStatus postageStatus, PostalItemStatus newStatus) {
        postageStatus.setPostalItemStatus(newStatus);
        postageStatusRepository.saveAndFlush(postageStatus);
    }


}
