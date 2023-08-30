package net.example.postaltrackingservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.postaltrackingservice.model.dto.PostalItemCreateDto;
import net.example.postaltrackingservice.model.dto.PostalItemReadDto;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.service.PostalItemService;
import net.example.postaltrackingservice.web.WebConfigurationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static net.example.postaltrackingservice.model.enums.PostalItemType.LETTER;
import static net.example.postaltrackingservice.web.WebConfigurationTest.API_VERSION;
import static net.example.postaltrackingservice.web.WebTestUtil.getMvcResponseContent;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(WebConfigurationTest.class)
@DisplayName("Тест контроллера почтовых отправлений")
class PostalItemControllerTest extends AbstractControllerTest {

    @Autowired
    PostalItemService postalItemService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест регистрации почтового отправления, код ответа 201")
    void create() throws Exception {

        var createDto = new PostalItemCreateDto(
                LETTER,
                "Иванов Иван Иванович",
                "1001001",
                "Волгоград, ул. Ницше, 101");

        var readDto = new PostalItemReadDto(
                1L,
                createDto.type(),
                createDto.recipient(),
                createDto.indexOfRecipient(),
                createDto.addressOfRecipient(),
                PostalItemStatus.REGISTERED);

        Mockito.when(postalItemService.registration(createDto))
                .thenReturn(readDto);

        MvcResult mvcResult = mockMvc.perform(post(API_VERSION + "/postal-item")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andReturn();

        var actualResult = getMvcResponseContent(mvcResult, PostalItemReadDto.class);
        assertEquals(readDto, actualResult);

    }

    @Test
    @DisplayName("Тест когда почтовое отправление не валидно ожидаем код ответа 400")
    void when_postalItemIsNotValid_expect_isBadRequest() throws Exception {

        var createDto = new PostalItemCreateDto(
                LETTER, "", "10", " ");

        mockMvc.perform(post(API_VERSION + "/postal-item")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

}
