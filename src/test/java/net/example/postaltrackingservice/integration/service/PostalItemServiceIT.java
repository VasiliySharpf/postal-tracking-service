package net.example.postaltrackingservice.integration.service;

import net.example.postaltrackingservice.exception.PostOfficeNotFoundException;
import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.integration.annotation.IT;
import net.example.postaltrackingservice.model.dto.PostalItemCreateDto;
import net.example.postaltrackingservice.model.dto.PostalItemReadDto;
import net.example.postaltrackingservice.model.entity.PostageStatus;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.repository.PostageStatusRepository;
import net.example.postaltrackingservice.service.PostalItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.*;
import static net.example.postaltrackingservice.model.enums.PostalItemType.PACKAGE;
import static org.junit.jupiter.api.Assertions.*;

@IT
@DisplayName("Тест сервиса почтовых отправлений")
class PostalItemServiceIT extends IntegrationTestBase {

    @Autowired
    PostalItemService postalItemService;
    @Autowired
    PostageStatusRepository postageStatusRepository;


    @Test
    @DisplayName("Тест успешной регистрации почтового отправления")
    void createPostalItem() {

        var expectedResult = new PostalItemCreateDto(
                PACKAGE, "Иванов Иван Иванович", POST_OFFICE_1, "Москва, ул. Рабочая, 20");

        PostalItemReadDto actualResult = postalItemService.registration(expectedResult);
        assertEquals(expectedResult.type(), actualResult.type());
        assertEquals(expectedResult.recipient(), actualResult.recipient());
        assertEquals(expectedResult.indexOfRecipient(), actualResult.indexOfRecipient());
        assertEquals(expectedResult.addressOfRecipient(), actualResult.addressOfRecipient());
        assertNotNull(actualResult.id());

        Optional<PostageStatus> status = postageStatusRepository.findById(actualResult.id());
        assertTrue(status.isPresent());
        assertEquals(PostalItemStatus.REGISTERED, status.get().getPostalItemStatus());

    }

    @Test
    @DisplayName("Тест регистрации почтового отправления с индексом несуществующего отделения")
    void when_postOfficeNotFound_expect_PostOfficeNotFoundException() {
        var expectedResult = new PostalItemCreateDto(
                PACKAGE, "Иванов Иван Иванович", POST_OFFICE_NOT_FOUND, "Москва, ул. Рабочая, 20");
        var exception = assertThrows(PostOfficeNotFoundException.class,
                () -> postalItemService.registration(expectedResult));
        assertNotNull(exception.getMessage());
    }

}