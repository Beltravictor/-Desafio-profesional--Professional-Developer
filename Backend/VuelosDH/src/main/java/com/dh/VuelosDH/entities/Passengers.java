package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Passengers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengers_id")
    private Long id;

    @Column(name = "Adults")
    private Integer adults;
    @Column(name = "Minors")
    private Integer minors;
    @Column(name = "Babies")
    private Integer babies;

}
