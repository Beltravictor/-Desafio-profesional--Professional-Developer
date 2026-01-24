package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "destinations_id")
    private Destinations destination;

}
