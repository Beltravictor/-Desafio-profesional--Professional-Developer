package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Policies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Policies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policies_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "destinations_id")
    private Destinations destination;
}
