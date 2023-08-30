package net.example.postaltrackingservice.integration.repository;

import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.integration.annotation.IT;
import net.example.postaltrackingservice.model.entity.PostageStatus;
import net.example.postaltrackingservice.repository.PostageStatusRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.*;
import static net.example.postaltrackingservice.model.enums.PostalItemStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@IT
@DisplayName("Тест репозитория статусов почтовых отправлений")
public class PostageStatusRepositoryTest extends IntegrationTestBase {

    @Autowired
    PostageStatusRepository postageStatusRepository;

    @Test
    @DisplayName("Тест поиска статуса почтового отправления по идентификатору")
    void findById() {
        Optional<PostageStatus> status = postageStatusRepository.findById(POSTAL_ITEM_ID_2);
        assertTrue(status.isPresent());
        assertEquals(IN_POST_OFFICE, status.get().getPostalItemStatus());
    }

    @Test
    @DisplayName("Тест сохранения статуса почтового отправления")
    void save() {
        var expectedStatus = PostageStatus.builder()
                .postalItemID(POSTAL_ITEM_ID_3)
                .postalItemStatus(RECEIVED_BY_ADDRESSEE)
                .build();

        PostageStatus actualStatus = postageStatusRepository.save(expectedStatus);
        assertNotNull(actualStatus);
        assertEquals(RECEIVED_BY_ADDRESSEE, actualStatus.getPostalItemStatus());
    }



}
