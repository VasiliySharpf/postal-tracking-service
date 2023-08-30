package net.example.postaltrackingservice.integration.repository;

import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.model.entity.Address;
import net.example.postaltrackingservice.repository.AddressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static net.example.postaltrackingservice.integration.TestData.ADDRESS_1;
import static net.example.postaltrackingservice.integration.TestData.ADDRESS_ID_1;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест репозитория адресов")
public class AddressRepositoryTest extends IntegrationTestBase {

    @Autowired
    AddressRepository addressRepository;

    @Test
    @DisplayName("Тест поиска адреса по описанию")
    void findByDescription() {

        var addressExpected = Address.builder()
                .id(ADDRESS_ID_1)
                .description(ADDRESS_1)
                .build();

        Optional<Address> addressActual = addressRepository.findByDescription(ADDRESS_1);
        assertTrue(addressActual.isPresent());
        assertEquals(addressExpected, addressActual.get());

    }

}
