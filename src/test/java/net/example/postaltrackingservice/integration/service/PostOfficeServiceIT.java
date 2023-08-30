package net.example.postaltrackingservice.integration.service;

import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.integration.annotation.IT;
import net.example.postaltrackingservice.mapper.PostOfficeMapper;
import net.example.postaltrackingservice.model.dto.PostOfficeDto;
import net.example.postaltrackingservice.model.entity.PostOffice;
import net.example.postaltrackingservice.repository.PostOfficeRepository;
import net.example.postaltrackingservice.service.PostOfficeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.POST_OFFICE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@IT
@DisplayName("Тест сервиса почтовых отделений")
class PostOfficeServiceIT extends IntegrationTestBase {

    @Autowired
    PostOfficeService postOfficeService;
    @Autowired
    PostOfficeRepository postOfficeRepository;
    @Autowired
    PostOfficeMapper postOfficeMapper;


    @Test
    @DisplayName("Тест создания почтового отделения")
    void create() {

        var expectedPostOffice = new PostOfficeDto(
                POST_OFFICE_NOT_FOUND, "Почтовое отделение 1000000", "Вологда, ул. Кирова, 1");

        assertTrue(postOfficeRepository.findById(POST_OFFICE_NOT_FOUND).isEmpty());
        boolean result = postOfficeService.create(expectedPostOffice);
        assertTrue(result);

        Optional<PostOffice> actualPostOffice = postOfficeRepository.findById(POST_OFFICE_NOT_FOUND);
        assertTrue(actualPostOffice.isPresent());
        assertEquals(expectedPostOffice, postOfficeMapper.mapToDto(actualPostOffice.get()));
    }
}