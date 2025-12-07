package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Characteristics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Characteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "characteristics_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "characteristics", cascade = CascadeType.ALL)
    private List<Destinations_Characteristics> destinations = new ArrayList<>();

    @Column(name = "address")
    private String address;
}
