package net.example.postaltrackingservice.mapper;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.model.dto.PostOfficeDto;
import net.example.postaltrackingservice.model.entity.PostOffice;
import net.example.postaltrackingservice.service.AddressService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostOfficeMapper implements Mapper<PostOfficeDto, PostOffice> {

    private final AddressService addressService;

    @Override
    public PostOffice map(PostOfficeDto dto) {
        return new PostOffice(
                dto.index(),
                dto.name(),
                addressService.findOrCreateAddress(dto.address()));
    }

    public PostOfficeDto mapToDto(PostOffice postOffice) {
        return new PostOfficeDto(
                postOffice.getIndex(),
                postOffice.getName(),
                postOffice.getAddress().getDescription());
    }
}
