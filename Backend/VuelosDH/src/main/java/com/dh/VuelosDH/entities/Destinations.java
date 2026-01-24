package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Destinations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private List<Destinations_Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destinations_Characteristics> characteristics = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "sample_price")
    private Double sample_price;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    private List<Flights> origin;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Flights> destination;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Policies> policies;

    @OneToMany(mappedBy = "destination")
    private List<UserReviews> reviews;

    public void addCharacteristic(Characteristics characteristic) {
        Destinations_Characteristics dc = Destinations_Characteristics.builder()
                .destination(this)
                .characteristics(characteristic)
                .build();

        characteristics.add(dc);
    }

    public void addCategory(Category category) {
        Destinations_Category dc = Destinations_Category.builder()
                .destination(this)
                .category(category)
                .build();

        categories.add(dc);
    }

    public void addImage(String url) {
        Images img = Images.builder()
                .destination(this)
                .url(url)
                .build();

        images.add(img);
    }

}
