package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.TicketsDTO;
import com.dh.VuelosDH.entities.Tickets;
import org.springframework.stereotype.Component;

@Component
public class TicketsMapper {

    public TicketsDTO toDto(Tickets ticket) {
        return TicketsDTO.builder()
                .id(ticket.getId())
                .seatNumber(ticket.getSeatNumber())
                .status(ticket.getStatus())
                .passengersClasses(ticket.getPassengersClasses())
                .creationDate(ticket.getCreationDate())
                .reservation_id(ticket.getReservations().getId())
                .passenger_id(ticket.getPassengers() != null ? ticket.getPassengers().getId() : null)
                .flight_id(ticket.getFlight().getId())
                .build();
    }

    public Tickets toTicket(TicketsDTO dto) {
        return Tickets.builder()
                .id(dto.getId())
                .seatNumber(dto.getSeatNumber())
                .status(dto.getStatus())
                .passengersClasses(dto.getPassengersClasses())
                .build();
    }
}
