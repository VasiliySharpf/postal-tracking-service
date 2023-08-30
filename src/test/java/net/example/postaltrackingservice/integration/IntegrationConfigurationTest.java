package net.example.postaltrackingservice.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;


@TestConfiguration
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class IntegrationConfigurationTest {
}

