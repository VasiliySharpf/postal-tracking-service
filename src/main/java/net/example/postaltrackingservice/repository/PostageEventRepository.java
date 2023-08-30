package net.example.postaltrackingservice.repository;

import net.example.postaltrackingservice.model.entity.PostageEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostageEventRepository extends JpaRepository<PostageEvent, Long> {

    @Query(nativeQuery = true,
            value =
            "SELECT " +
            "    * " +
            "FROM tracking.postage_events " +
            "WHERE postal_item_id = :postalItemID " +
            "ORDER BY event_date DESC " +
            "LIMIT 1")
    Optional<PostageEvent> findLastEventByPostalItem(@Param("postalItemID") Long postalItemID);

    @Query(value =
            "select pe from PostageEvent pe " +
            "where pe.postalItem.id = ?1 " +
            "order by pe.eventDate")
    List<PostageEvent> findAllByPostalItemOrderByEventDate(long postalItemID);

}
