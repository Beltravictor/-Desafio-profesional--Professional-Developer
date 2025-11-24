package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Destinations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Destinations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destinations_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images = new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Destinations_Category> categories = new HashSet<>();

    @Column(name = "description")
    private String description;

    @Column(name = "sample_price")
    private Double sample_price;

    @Column(name = "rating")
    private Float rating;

    public void setImages(List<Images> images) {
        this.images.clear();
        if (images != null) {
            for (Images img : images) {
                img.setDestination(this);
                this.images.add(img);
            }
        }
    }

    public void setCategories(Set<Destinations_Category> categories) {
        this.categories.clear();
        if (categories != null) {
            for (Destinations_Category d_c : categories) {
                d_c.setDestination(this);
                this.categories.add(d_c);
            }
        }
    }
}
