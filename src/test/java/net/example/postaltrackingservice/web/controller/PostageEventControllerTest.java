package net.example.postaltrackingservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.postaltrackingservice.model.dto.PostageEventCreateDto;
import net.example.postaltrackingservice.model.dto.PostageEventReadDto;
import net.example.postaltrackingservice.service.PostageEventService;
import net.example.postaltrackingservice.web.WebConfigurationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Clock;
import java.time.OffsetDateTime;

import static net.example.postaltrackingservice.model.enums.EventType.ARRIVAL;
import static net.example.postaltrackingservice.web.WebConfigurationTest.API_VERSION;
import static net.example.postaltrackingservice.web.WebTestUtil.getMvcResponseContent;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(WebConfigurationTest.class)
@DisplayName("Тест контроллера почтовых событий")
public class PostageEventControllerTest extends AbstractControllerTest {

    @Autowired
    PostageEventService postageEventService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест создания почтового события, код ответа 201")
    void createPostageEvent() throws Exception {

        var createDto = new PostageEventCreateDto(1L, "1001001");
        var expectedResult = new PostageEventReadDto(
                1L,
                createDto.postalItemID(),
                createDto.postOfficeCode(),
                "Почтовое отделение 1001001",
                OffsetDateTime.now(Clock.systemUTC()),
                ARRIVAL);

        Mockito.when(postageEventService.createPostageEvent(createDto, ARRIVAL))
                .thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc.perform(post(API_VERSION + "/postage-event")
                        .param("eventType", String.valueOf(ARRIVAL))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andReturn();

        var actualResult = getMvcResponseContent(mvcResult, PostageEventReadDto.class);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    @DisplayName("Тест когда почтовое событие не валидно, код ответа 400")
    void when_PostageEventIsNotVaild_expect_isBadRequest() throws Exception {
        var createDto = new PostageEventCreateDto(1L, " ");
        mockMvc.perform(post(API_VERSION + "/postage-event")
                        .param("eventType", String.valueOf(ARRIVAL))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Тест получения почтового отправления адресатом, код ответа 200")
    void receiptByAddressee() throws Exception {
        var createDto = new PostageEventCreateDto(1L, "1001001");
        mockMvc.perform(post(API_VERSION + "/postage-event/receipt")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk());
    }







}
