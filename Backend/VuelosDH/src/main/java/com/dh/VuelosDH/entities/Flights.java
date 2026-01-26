package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Flights")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_origin_id")
    private Destinations origin;

    @ManyToOne
    @JoinColumn(name = "destination_destination_id")
    private Destinations destination;

    @Column(name = "date_of_completion")
    private Date date_of_completion;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "return_date")
    private Date return_date;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @OneToMany(mappedBy = "startFlight", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservations> startFlight;

    @OneToMany(mappedBy = "returnFlight", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservations> returnFlight;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Tickets> tickets;

    public void addTicket(Tickets ticket) {
        if(tickets == null)
            this.setTickets(new ArrayList<>());
        tickets.add(ticket);
        ticket.setFlight(this);
    }

    public void removeTicket(Tickets ticket) {
        tickets.remove(ticket);
        ticket.setFlight(null);
    }

    public void addStartFlight(Reservations reservation) {
        if(startFlight == null)
            this.setStartFlight(new ArrayList<>());
        startFlight.add(reservation);
        reservation.setStartFlight(this);
    }

    public void removeStartFlight(Reservations reservation) {
        startFlight.remove(reservation);
        reservation.setStartFlight(null);
    }

    public void addReturnFlight(Reservations reservation) {
        if(returnFlight == null)
            this.setReturnFlight(new ArrayList<>());
        returnFlight.add(reservation);
        reservation.setReturnFlight(this);
    }

    public void removeReturnFlight(Reservations reservation) {
        returnFlight.remove(reservation);
        reservation.setReturnFlight(null);
    }

}
