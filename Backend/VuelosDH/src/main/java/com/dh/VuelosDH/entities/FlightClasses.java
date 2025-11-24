package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Flightclasses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flightclasses_id")
    private Long id;

    @Column(name = "name")
    private String name;

}
