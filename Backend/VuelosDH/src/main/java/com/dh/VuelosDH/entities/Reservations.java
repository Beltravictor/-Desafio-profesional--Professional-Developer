package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservations_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private Status reservationStatus;

    @Column(name = "economy_class")
    private Integer economyClass;

    @Column(name = "premium_economy_class")
    private Integer premium_economyClass;

    @Column(name = "business_class")
    private Integer businessClass;

    @Column(name = "first_class")
    private Integer firstClass;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "return_date")
    private Date return_date;

    @ManyToOne
    @JoinColumn(name = "start_flight_id")
    private Flights startFlight;

    @ManyToOne
    @JoinColumn(name = "return_flight_id")
    private Flights returnFlight;

    @OneToMany(mappedBy = "reservations")
    private List<Tickets> tickets;

    public void addTicket(Tickets ticket) {
        ticket.setReservations(this);
        tickets.add(ticket);
    }

    public void removeTicket(Tickets ticket) {
        tickets.remove(ticket);
        ticket.setReservations(null);
    }

    public boolean isPaid() {
        return reservationStatus == Status.PAID;
    }
}
