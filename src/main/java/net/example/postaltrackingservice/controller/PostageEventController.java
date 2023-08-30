package net.example.postaltrackingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.postaltrackingservice.model.dto.PostageEventCreateDto;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.model.enums.EventType;
import net.example.postaltrackingservice.service.PostageEventService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/postage-event")
@RequiredArgsConstructor
public class PostageEventController {

    private final PostageEventService postageEventService;


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Регистрации прибытия/выбытия почтового отправления.")
    public PostageEventReadDto createPostageEvent(@RequestBody @Validated PostageEventCreateDto postageEventCreateDto,
                                                  @RequestParam @NotNull EventType eventType) {
        log.info("Request [POST] /postage-event eventType = {}", eventType);
        return postageEventService.createPostageEvent(postageEventCreateDto, eventType);
    }

    @PostMapping("/receipt")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение почтового отправления адресатом.")
    public void receiptByAddressee(@RequestBody @Validated PostageEventCreateDto postageEventCreateDto) {
        log.info("Request [POST] /postage-event/receipt");
        postageEventService.receivePostalItemByAddressee(postageEventCreateDto);
    }

}
