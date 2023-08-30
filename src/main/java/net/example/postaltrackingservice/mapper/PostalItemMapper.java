package net.example.postaltrackingservice.mapper;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.model.dto.PostalItemCreateDto;
import net.example.postaltrackingservice.model.dto.PostalItemReadDto;
import net.example.postaltrackingservice.model.entity.PostalItem;
import net.example.postaltrackingservice.service.AddressService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostalItemMapper implements Mapper<PostalItemCreateDto, PostalItem> {

    private final AddressService addressService;

    @Override
    public PostalItem map(PostalItemCreateDto dto) {
        return PostalItem.builder()
                .type(dto.type())
                .recipient(dto.recipient())
                .indexOfRecipient(dto.indexOfRecipient())
                .addressOfRecipient(addressService.findOrCreateAddress(dto.addressOfRecipient()))
                .build();
    }

    public PostalItemReadDto mapToReadDto(PostalItem postalItem, PostalItemStatus postalItemStatus) {
        return new PostalItemReadDto(
                postalItem.getId(),
                postalItem.getType(),
                postalItem.getRecipient(),
                postalItem.getIndexOfRecipient(),
                postalItem.getAddressOfRecipient().getDescription(),
                postalItemStatus
        );
    }
}
