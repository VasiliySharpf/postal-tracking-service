package net.example.postaltrackingservice.web.controller;


import net.example.postaltrackingservice.controller.PostOfficeController;
import net.example.postaltrackingservice.controller.PostageEventController;
import net.example.postaltrackingservice.controller.PostalItemController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;

@WebMvcTest({
        PostageEventController.class, PostalItemController.class, PostOfficeController.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public abstract class AbstractControllerTest {
}
