package net.example.postaltrackingservice.integration.repository;

import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.integration.annotation.IT;
import net.example.postaltrackingservice.model.entity.PostOffice;
import net.example.postaltrackingservice.repository.PostOfficeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.POST_OFFICE_1;
import static net.example.postaltrackingservice.integration.TestData.POST_OFFICE_1_NAME;
import static org.junit.jupiter.api.Assertions.*;

@IT
@DisplayName("Тест репозитория почтовых отделений")
public class PostOfficeRepositoryTest extends IntegrationTestBase {

    @Autowired
    PostOfficeRepository postOfficeRepository;

    @Test
    @DisplayName("Тест поиска почтового отделения по идентификатору")
    void findById() {
        Optional<PostOffice> postOffice = postOfficeRepository.findById(POST_OFFICE_1);
        assertTrue(postOffice.isPresent());
        assertEquals(POST_OFFICE_1, postOffice.get().getIndex());
        assertEquals(POST_OFFICE_1_NAME, postOffice.get().getName());
    }
}
