package net.example.postaltrackingservice.repository;

import net.example.postaltrackingservice.model.entity.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOffice, String> {
}
