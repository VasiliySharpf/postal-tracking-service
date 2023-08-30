package net.example.postaltrackingservice.repository;

import net.example.postaltrackingservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByDescription(String addressDescription);
}
