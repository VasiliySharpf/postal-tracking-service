package net.example.postaltrackingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.postaltrackingservice.model.enums.PostalItemType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "postal_items")
public class PostalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private PostalItemType type;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "index_of_recipient")
    private String indexOfRecipient;

    @JoinColumn(name = "address_of_recipient")
    @ManyToOne(fetch = FetchType.LAZY)
    private Address addressOfRecipient;

}
