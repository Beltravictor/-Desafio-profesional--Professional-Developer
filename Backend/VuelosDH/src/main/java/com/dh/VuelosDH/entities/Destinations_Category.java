package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Destinations_Category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Destinations_Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destinations_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destinations_id")
    private Destinations destination;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
