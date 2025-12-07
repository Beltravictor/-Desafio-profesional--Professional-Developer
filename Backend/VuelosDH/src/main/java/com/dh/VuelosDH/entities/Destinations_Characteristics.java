package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Destinations_Characteristics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
