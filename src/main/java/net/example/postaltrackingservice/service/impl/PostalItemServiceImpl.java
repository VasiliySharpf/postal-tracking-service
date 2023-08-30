package net.example.postaltrackingservice.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.handler.EntityPresenceHandler;
import net.example.postaltrackingservice.mapper.PostalItemMapper;
import net.example.postaltrackingservice.model.entity.PostageStatus;
import net.example.postaltrackingservice.model.dto.PostalItemCreateDto;
import net.example.postaltrackingservice.model.dto.PostalItemReadDto;
import net.example.postaltrackingservice.model.entity.PostalItem;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.repository.PostageStatusRepository;
import net.example.postaltrackingservice.repository.PostalItemRepository;
import net.example.postaltrackingservice.service.PostalItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static net.example.postaltrackingservice.model.enums.PostalItemStatus.REGISTERED;

@Service
@RequiredArgsConstructor
public class PostalItemServiceImpl implements PostalItemService {

    private final PostalItemRepository postalItemRepository;
    private final PostageStatusRepository postageStatusRepository;
    private final PostalItemMapper postalItemMapper;
    private final EntityPresenceHandler entityPresenceHandler;

    @Transactional
    @Override
    public PostalItemReadDto registration(@NotNull PostalItemCreateDto postalItemCreateDto) {

        entityPresenceHandler.checkPostOfficeByIndex(postalItemCreateDto.indexOfRecipient());

        PostalItem postalItem = postalItemRepository
                .saveAndFlush(postalItemMapper.map(postalItemCreateDto));
        postageStatusRepository.save(new PostageStatus(postalItem.getId(), REGISTERED));

        return postalItemMapper.mapToReadDto(postalItem, REGISTERED);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<PostalItemReadDto> findById(long id) {

        PostalItemStatus status = postageStatusRepository.findById(id)
                .map(PostageStatus::getPostalItemStatus)
                .orElse(null);

        return postalItemRepository.findById(id)
                .map(postalItem -> postalItemMapper.mapToReadDto(postalItem, status));
    }

}
