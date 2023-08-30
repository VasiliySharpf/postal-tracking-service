package net.example.postaltrackingservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.MvcResult;


public class WebTestUtil {

    static ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    public static <T> T getMvcResponseContent(MvcResult result, Class<T> clazz) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), clazz);
    }
}
