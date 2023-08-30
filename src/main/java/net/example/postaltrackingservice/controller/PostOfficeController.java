package net.example.postaltrackingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.postaltrackingservice.model.dto.PostOfficeDto;
import net.example.postaltrackingservice.service.PostOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/post-office")
@RequiredArgsConstructor
public class PostOfficeController {

    private final PostOfficeService postOfficeService;

    @PostMapping
    @Operation(summary = "Создание почтового отделения.")
    public ResponseEntity<?> create(@RequestBody @Validated PostOfficeDto postOfficeDto) {
        log.info("Request [POST] /post-office");
        return postOfficeService.create(postOfficeDto)
                ? ResponseEntity.created(URI.create("/postoffice/" + postOfficeDto.index())).build()
                : ResponseEntity.badRequest().build();
    }

}
