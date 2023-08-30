package net.example.postaltrackingservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.postaltrackingservice.controller.PostOfficeController;
import net.example.postaltrackingservice.controller.PostalItemController;
import net.example.postaltrackingservice.model.dto.PostOfficeDto;
import net.example.postaltrackingservice.service.PostOfficeService;
import net.example.postaltrackingservice.web.WebConfigurationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static net.example.postaltrackingservice.web.WebConfigurationTest.API_VERSION;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(WebConfigurationTest.class)
@DisplayName("Тест контроллера почтовых отделений")
class PostOfficeControllerTest extends AbstractControllerTest {

    @Autowired
    PostOfficeService postOfficeService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест создания почтового отделения, код ответа 201")
    void create() throws Exception {

        var postOfficeDto = new PostOfficeDto(
                "1001001", "Почтовое отделение 1001001", "Волгоград, ул. Ницше, 1");

        Mockito.when(postOfficeService.create(postOfficeDto))
                .thenReturn(true);

        mockMvc.perform(post(API_VERSION + "/post-office")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postOfficeDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Тест когда почтовое отправление не валидно ожидаем код ответа 400")
    void when_postalItemIsNotValid_expect_isBadRequest() throws Exception {

        var postOfficeDto = new PostOfficeDto(
                "100", " ", " ");

        mockMvc.perform(post(API_VERSION + "/post-office")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postOfficeDto)))
                .andExpect(status().isBadRequest());
    }
}