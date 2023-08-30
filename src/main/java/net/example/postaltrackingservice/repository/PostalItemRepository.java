package net.example.postaltrackingservice.repository;

import net.example.postaltrackingservice.model.entity.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
}
