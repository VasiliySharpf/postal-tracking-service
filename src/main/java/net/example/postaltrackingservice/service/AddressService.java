package net.example.postaltrackingservice.service;

import net.example.postaltrackingservice.model.entity.Address;

public interface AddressService {

    Address findOrCreateAddress(String description);

}
