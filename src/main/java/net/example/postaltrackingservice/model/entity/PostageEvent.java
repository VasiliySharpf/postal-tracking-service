package net.example.postaltrackingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.postaltrackingservice.model.enums.EventType;

import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "postage_events")
public class PostageEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "postal_item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PostalItem postalItem;

    @JoinColumn(name = "post_office_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PostOffice postOffice;

    @Column(name = "event_date")
    private OffsetDateTime eventDate;

    @Column(name = "event")
    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

}
