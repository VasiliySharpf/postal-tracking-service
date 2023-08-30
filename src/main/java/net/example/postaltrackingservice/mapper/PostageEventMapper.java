package net.example.postaltrackingservice.mapper;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.model.dto.PostageEventCreateDto;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.model.entity.PostageEvent;
import net.example.postaltrackingservice.repository.PostOfficeRepository;
import net.example.postaltrackingservice.repository.PostalItemRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostageEventMapper implements Mapper<PostageEventCreateDto, PostageEvent> {

    private final PostalItemRepository postalItemRepository;
    private final PostOfficeRepository postOfficeRepository;

    @Override
    public PostageEvent map(PostageEventCreateDto dto) {
        return PostageEvent.builder()
                .postalItem(postalItemRepository.getReferenceById(dto.postalItemID()))
                .postOffice(postOfficeRepository.getReferenceById(dto.postOfficeCode()))
                .build();
    }

    public PostageEventReadDto mapToReadDto(PostageEvent postageEvent) {
        return new PostageEventReadDto(
                postageEvent.getId(),
                postageEvent.getPostalItem().getId(),
                postageEvent.getPostOffice().getIndex(),
                postageEvent.getPostOffice().getName(),
                postageEvent.getEventDate(),
                postageEvent.getEventType());
    }
}
