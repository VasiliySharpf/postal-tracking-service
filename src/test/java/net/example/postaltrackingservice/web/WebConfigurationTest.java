package net.example.postaltrackingservice.web;

import net.example.postaltrackingservice.service.PostOfficeService;
import net.example.postaltrackingservice.service.PostageEventService;
import net.example.postaltrackingservice.service.PostalItemService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

@TestConfiguration
public class WebConfigurationTest {

    @MockBean
    PostalItemService postalItemService;
    @MockBean
    PostOfficeService postOfficeService;
    @MockBean
    PostageEventService postageEventService;
    public static String API_VERSION = "/api/v1";


    @Bean
    public MockMvc mockMvc(WebApplicationContext webApplicationContext) {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();
    }

}
