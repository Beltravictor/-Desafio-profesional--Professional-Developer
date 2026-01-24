package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Passengers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengers_id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private Document_Type documentType;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "birthdate")
    private Date birthdate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "passengers")
    private List<Tickets> tickets;

    public void addTicket(Tickets ticket) {
        ticket.setPassengers(this);
        tickets.add(ticket);
    }

    public void removeTicket(Tickets ticket) {
        tickets.remove(ticket);
        ticket.setPassengers(null);
    }
}
