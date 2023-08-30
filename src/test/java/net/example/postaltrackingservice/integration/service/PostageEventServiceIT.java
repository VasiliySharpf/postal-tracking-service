package net.example.postaltrackingservice.integration.service;

import net.example.postaltrackingservice.exception.EventRegistrationException;
import net.example.postaltrackingservice.exception.PostOfficeNotFoundException;
import net.example.postaltrackingservice.exception.PostalItemNotFoundException;
import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.integration.annotation.IT;
import net.example.postaltrackingservice.model.dto.PostageEventCreateDto;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.model.entity.PostageEvent;
import net.example.postaltrackingservice.model.entity.PostageStatus;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.repository.PostageEventRepository;
import net.example.postaltrackingservice.repository.PostageStatusRepository;
import net.example.postaltrackingservice.service.PostageEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.*;
import static net.example.postaltrackingservice.model.enums.EventType.*;
import static net.example.postaltrackingservice.model.enums.PostalItemStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@IT
@DisplayName("Тест сервиса почтовых событий")
class PostageEventServiceIT extends IntegrationTestBase {

    @Autowired
    PostageEventService postageEventService;
    @Autowired
    PostageStatusRepository postageStatusRepository;
    @Autowired
    PostageEventRepository postageEventRepository;

    @Test
    @DisplayName("Тест успешного создания события прибытия почтового отправления")
    void createArrivalEvent() {

        assertPostageStatusBefore(POSTAL_ITEM_ID_1, IN_POST_OFFICE);

        var expectedResult = new PostageEventCreateDto(POSTAL_ITEM_ID_1, POST_OFFICE_1);
        var actualResult = postageEventService.createPostageEvent(expectedResult, ARRIVAL);
        checkPostalEventFields(expectedResult, actualResult);

        assertPostageStatusAfter(POSTAL_ITEM_ID_1, IN_POST_OFFICE);

    }

    @Test
    @DisplayName("Тест успешного создания события выбытия почтового отправления")
    void createDepartureEvent() {
        assertPostageStatusBefore(POSTAL_ITEM_ID_5, ON_WAY);
        var expectedResult = new PostageEventCreateDto(POSTAL_ITEM_ID_5, POST_OFFICE_1);
        var actualResult = postageEventService.createPostageEvent(expectedResult, DEPARTURE);
        checkPostalEventFields(expectedResult, actualResult);
        assertPostageStatusAfter(POSTAL_ITEM_ID_5, ON_WAY);
    }

    @Test
    @DisplayName("Тест успешного создания события получения почтового отправления адресатом")
    void receivePostalItemByAddressee() {

        assertPostageStatusBefore(POSTAL_ITEM_ID_5, RECEIVED_BY_ADDRESSEE);
        var dto = new PostageEventCreateDto(POSTAL_ITEM_ID_5, POST_OFFICE_1);
        postageEventService.receivePostalItemByAddressee(dto);

        Optional<PostageStatus> status = postageStatusRepository.findById(POSTAL_ITEM_ID_5);
        assertTrue(status.isPresent());
        assertEquals(RECEIVED_BY_ADDRESSEE, status.get().getPostalItemStatus());

        Optional<PostageEvent> postageEvent = postageEventRepository.findLastEventByPostalItem(POSTAL_ITEM_ID_5);
        assertTrue(postageEvent.isPresent());
        assertEquals(DEPARTURE, postageEvent.get().getEventType());
        assertPostageStatusAfter(POSTAL_ITEM_ID_5, RECEIVED_BY_ADDRESSEE);

    }

    @Test
    @DisplayName("Тест когда почтового отделения не существует ожидаем исключение PostOfficeNotFoundException")
    void when_PostOfficeDoesNotExist_expect_PostOfficeNotFoundException() {
        var createDto = new PostageEventCreateDto(POSTAL_ITEM_ID_5, POST_OFFICE_NOT_FOUND);
        var exception = assertThrows(PostOfficeNotFoundException.class,
                () -> postageEventService.createPostageEvent(createDto, DEPARTURE));
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Тест если почтового отправления не существует ожидаем исключение PostalItemNotFoundException")
    void when_PostalItemDoesNotExist_expect_PostalItemNotFoundException() {
        var createDto = new PostageEventCreateDto(POSTAL_ITEM_ID_NOT_FOUND, POST_OFFICE_1);
        var exception = assertThrows(PostalItemNotFoundException.class,
                () -> postageEventService.createPostageEvent(createDto, DEPARTURE));
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Тест прибытия почтового отправления когда оно уже в указанном почтовом отделении")
    void when_PostalItemInPostOffice_expect_EventRegistrationException() {
        var createDto = new PostageEventCreateDto(POSTAL_ITEM_ID_5, POST_OFFICE_1);
        var exception = assertThrows(EventRegistrationException.class,
                () -> postageEventService.createPostageEvent(createDto, ARRIVAL));
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Тест выбытия почтового отправления когда оно не найдено в указанном почтовом отделении")
    void when_PostalItemNotFoundInPostOffice_expect_EventRegistrationException() {
        var createDto = new PostageEventCreateDto(POSTAL_ITEM_ID_5, POST_OFFICE_2);
        var exception = assertThrows(EventRegistrationException.class,
                () -> postageEventService.createPostageEvent(createDto, DEPARTURE));
        assertNotNull(exception.getMessage());
    }

    void checkPostalEventFields(PostageEventCreateDto expectedResult, PostageEventReadDto actualResult) {
        assertEquals(expectedResult.postalItemID(), actualResult.postalItemID());
        assertEquals(expectedResult.postOfficeCode(), actualResult.postOfficeCode());
        assertNotNull(actualResult.id());
        assertNotNull(actualResult.postOfficeName());
        assertNotNull(actualResult.eventDate());
        assertNotNull(actualResult.event());
    }

    void assertPostageStatusBefore(Long postalItemID, PostalItemStatus postalItemStatus) {
        var postageStatus = postageStatusRepository.findById(postalItemID);
        assertTrue(postageStatus.isPresent());
        assertNotEquals(postalItemStatus, postageStatus.get().getPostalItemStatus());
    }

    void assertPostageStatusAfter(Long postalItemID, PostalItemStatus postalItemStatus) {
        var postageStatus = postageStatusRepository.findById(postalItemID);
        assertTrue(postageStatus.isPresent());
        assertEquals(postalItemStatus, postageStatus.get().getPostalItemStatus());
    }

}