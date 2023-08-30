package net.example.postaltrackingservice.service;

import net.example.postaltrackingservice.model.dto.PostageEventCreateDto;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.model.enums.EventType;

import java.util.List;

public interface PostageEventService {

    PostageEventReadDto createPostageEvent(PostageEventCreateDto postageEventCreateDto, EventType eventType);

    void receivePostalItemByAddressee(PostageEventCreateDto postageEventCreateDto);

    List<PostageEventReadDto> findAllEventsByPostalItemID(long postalItemID);

}
