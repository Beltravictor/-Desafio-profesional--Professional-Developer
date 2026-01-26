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
    private List<Images> images;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destinations_Category> categories;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destinations_Characteristics> characteristics;

    @Column(name = "description")
    private String description;

    @Column(name = "sample_price")
    private Double sample_price;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flights> origin;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flights> destination;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policies> policies;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReviews> reviews;

    public void addCharacteristic(Characteristics characteristic) {
        if(characteristics == null)
            setCharacteristics(new ArrayList<>());
        Destinations_Characteristics dc = Destinations_Characteristics.builder()
                .destination(this)
                .characteristics(characteristic)
                .build();

        characteristics.add(dc);
    }

    public void addCategory(Category category) {
        if(categories == null)
            setCategories(new ArrayList<>());
        Destinations_Category dc = Destinations_Category.builder()
                .destination(this)
                .category(category)
                .build();

        categories.add(dc);
    }

    public void addImage(String url) {
        if(images == null)
            setImages(new ArrayList<>());
        Images img = Images.builder()
                .destination(this)
                .url(url)
                .build();

        images.add(img);
    }

}
