package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tickets_id")
    private Long id;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "passengers_classes")
    private Passengers_Classes passengersClasses;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "flights_id")
    private Flights flight;

    @ManyToOne
    @JoinColumn(name = "reservations_id")
    private Reservations reservations;

    @ManyToOne
    @JoinColumn(name = "passengers_id")
    private Passengers passengers;

}
