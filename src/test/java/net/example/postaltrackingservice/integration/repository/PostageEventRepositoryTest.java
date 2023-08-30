package net.example.postaltrackingservice.integration.repository;

import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.mapper.PostageEventMapper;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.model.entity.PostageEvent;
import net.example.postaltrackingservice.repository.PostageEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест репозитория почтовых событий")
class PostageEventRepositoryTest extends IntegrationTestBase {

    @Autowired
    PostageEventRepository postageEventRepository;
    @Autowired
    PostageEventMapper postageEventMapper;


    @Test
    @DisplayName("Получение последнего события почтового отправления")
    void findLastEventByPostalItem() {

        Optional<PostageEvent> postageEvent = postageEventRepository.findLastEventByPostalItem(POSTAL_ITEM_ID_4);
        assertTrue(postageEvent.isPresent());
        assertEquals(LAST_EVENT_ID, postageEvent.get().getId());

    }

    @Test
    @DisplayName("Получение всех событий почтового отправления с сортировкой по дате")
    void findAllByPostalItemOrderByEventDate() {
        List<PostageEventReadDto> allEvents = postageEventRepository.findAllByPostalItemOrderByEventDate(POSTAL_ITEM_ID_4).stream()
                .map(postageEventMapper::mapToReadDto).toList();

        assertEquals(TOTAL_EVENTS, allEvents.size());
        assertEquals(ALL_EVENTS, allEvents);
    }


}