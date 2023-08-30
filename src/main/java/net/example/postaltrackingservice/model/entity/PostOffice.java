package net.example.postaltrackingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_offices")
public class PostOffice {

    @Id
    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "address")
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

}
