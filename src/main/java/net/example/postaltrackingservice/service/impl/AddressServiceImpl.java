package net.example.postaltrackingservice.service.impl;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.model.entity.Address;
import net.example.postaltrackingservice.repository.AddressRepository;
import net.example.postaltrackingservice.service.AddressService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address findOrCreateAddress(String description) {

        return addressRepository.findByDescription(description)
                .orElse(addressRepository.saveAndFlush(
                        Address.builder().description(description).build()));

    }
}
