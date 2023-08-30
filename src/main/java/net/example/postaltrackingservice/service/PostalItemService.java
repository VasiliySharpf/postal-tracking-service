package net.example.postaltrackingservice.service;

import net.example.postaltrackingservice.model.dto.PostalItemCreateDto;
import net.example.postaltrackingservice.model.dto.PostalItemReadDto;

import java.util.Optional;

public interface PostalItemService {

    PostalItemReadDto registration(PostalItemCreateDto postalItemCreateDto);

    Optional<PostalItemReadDto> findById(long id);

}
