package net.example.postaltrackingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.postaltrackingservice.model.dto.PostalItemCreateDto;
import net.example.postaltrackingservice.model.dto.PostalItemReadDto;
import net.example.postaltrackingservice.service.PostalItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/postal-item")
@RequiredArgsConstructor
public class PostalItemController {

    private final PostalItemService postalItemService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Регистрации почтового отправления.")
    public PostalItemReadDto registrationNewPostalItem(@RequestBody @Validated PostalItemCreateDto postalItemCreateDto) {
        log.info("Request [POST] /postal-item");
        return postalItemService.registration(postalItemCreateDto);
    }

    @GetMapping("{postalItemID}")
    @Operation(summary = "Получение почтового отправления по идентификатору.")
    public ResponseEntity<?> findById(@PathVariable("postalItemID") long id) {
        log.info("Request [GET] /postal-item/{}", id);
        return postalItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
