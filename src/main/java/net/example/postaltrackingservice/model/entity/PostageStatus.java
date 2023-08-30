package net.example.postaltrackingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "postage_statuses")
public class PostageStatus {

    @Id
    @Column(name = "postal_item_id")
    private Long postalItemID;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private PostalItemStatus postalItemStatus;

}
