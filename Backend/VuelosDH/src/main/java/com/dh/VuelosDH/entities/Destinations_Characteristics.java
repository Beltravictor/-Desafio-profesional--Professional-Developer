package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Destinations_Characteristics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Destinations_Characteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destinations_characteristics_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destinations_id")
    private Destinations destination;

    @ManyToOne
    @JoinColumn(name = "characteristics_id")
    private Characteristics characteristics;
}
