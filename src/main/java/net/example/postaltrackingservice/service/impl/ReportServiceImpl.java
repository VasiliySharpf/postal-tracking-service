package net.example.postaltrackingservice.service.impl;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.exception.PostageStatusNotFoundException;
import net.example.postaltrackingservice.model.dto.PostalItemHistoryDto;
import net.example.postaltrackingservice.model.entity.PostageStatus;
import net.example.postaltrackingservice.model.entity.PostalItem;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.repository.PostageStatusRepository;
import net.example.postaltrackingservice.repository.PostalItemRepository;
import net.example.postaltrackingservice.service.PostageEventService;
import net.example.postaltrackingservice.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final PostageStatusRepository postageStatusRepository;
    private final PostalItemRepository postalItemRepository;
    private final PostageEventService postageEventService;

    @Override
    public Optional<PostalItemHistoryDto> getPostalItemHistory(long postalItemID) {

        Optional<PostalItem> postalItem = postalItemRepository.findById(postalItemID);

        if (postalItem.isEmpty()) {
            return Optional.empty();
        }

        PostalItemStatus status = postageStatusRepository.findById(postalItemID)
                .map(PostageStatus::getPostalItemStatus)
                .orElseThrow(() -> new PostageStatusNotFoundException(
                        String.format("Не найден статус почтового отправления '%s'", postalItemID)));

        List<PostalItemHistoryDto.EventHistory> allEvents = postageEventService.findAllEventsByPostalItemID(postalItemID).stream()
                .map(event -> new PostalItemHistoryDto.EventHistory(
                        event.eventDate(),
                        event.postOfficeCode(),
                        event.postOfficeName(),
                        event.event()))
                .collect(toList());

        return Optional.of(new PostalItemHistoryDto(
                postalItem.get().getId(),
                postalItem.get().getType(),
                status,
                allEvents));
    }
}
