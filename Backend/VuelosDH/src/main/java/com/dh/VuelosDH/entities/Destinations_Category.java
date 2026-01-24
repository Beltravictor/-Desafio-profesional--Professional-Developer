package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Destinations_Category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
