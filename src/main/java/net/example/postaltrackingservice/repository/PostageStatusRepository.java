package net.example.postaltrackingservice.repository;

import net.example.postaltrackingservice.model.entity.PostageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostageStatusRepository extends JpaRepository<PostageStatus, Long> {

    @Query(nativeQuery = true, value =
    "SELECT * FROM tracking.postage_statuses WHERE postal_item_id = :postalItemID FOR UPDATE")
    Optional<PostageStatus> getPostageStatusForUpdate(@Param("postalItemID") Long postalItemID);

}
