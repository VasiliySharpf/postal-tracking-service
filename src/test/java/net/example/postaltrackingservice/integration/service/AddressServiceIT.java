package net.example.postaltrackingservice.integration.service;

import net.example.postaltrackingservice.integration.IntegrationTestBase;
import net.example.postaltrackingservice.model.entity.Address;
import net.example.postaltrackingservice.repository.AddressRepository;
import net.example.postaltrackingservice.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static net.example.postaltrackingservice.integration.TestData.ADDRESS_NEW;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Тест сервиса адресов")
public class AddressServiceIT extends IntegrationTestBase {

    @Autowired
    AddressService addressService;
    @Autowired
    AddressRepository addressRepository;

    @Test
    @DisplayName("Тест когда адрес не найден создаем новый")
    void findOrCreateAddress() {
        Address newAddress = addressService.findOrCreateAddress(ADDRESS_NEW);
        assertNotNull(newAddress);
        assertNotNull(newAddress.getId());
        assertEquals(newAddress.getDescription(), ADDRESS_NEW);
        assertTrue(addressRepository.findById(newAddress.getId()).isPresent());
        assertEquals(newAddress, addressService.findOrCreateAddress(ADDRESS_NEW));
    }

}
