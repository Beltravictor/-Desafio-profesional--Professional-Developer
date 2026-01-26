package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "User_Reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_reviews_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destinations destination;

    @Column(name = "review")
    private String review;

    @Column(name = "stars")
    private Float stars;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private Date creationDate;
}
