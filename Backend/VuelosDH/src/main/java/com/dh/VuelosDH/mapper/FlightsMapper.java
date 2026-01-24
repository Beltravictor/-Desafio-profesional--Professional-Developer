package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.FlightsDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.Flights;
import com.dh.VuelosDH.entities.Reservations;
import com.dh.VuelosDH.entities.Tickets;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightsMapper {

    public FlightsDTO toDto(Flights flights) {
        return FlightsDTO.builder()
                .id(flights.getId())
                .origin(flights.getOrigin().getId())
                .destination(flights.getDestination().getId())
                .date_of_completion(flights.getDate_of_completion())
                .departure_date(flights.getDeparture_date())
                .return_date(flights.getReturn_date())
                .totalSeats(flights.getTotalSeats())
                .availableSeats(flights.getAvailableSeats())
                .startFlight(flights.getStartFlight().stream()
                        .map(Reservations::getId)
                        .toList())
                .returnFlight(flights.getReturnFlight().stream()
                        .map(Reservations::getId)
                        .toList())
                .tickets(flights.getTickets().stream()
                        .map(Tickets::getId)
                        .toList())
                .build();
    }

    public Flights toFlight(FlightsDTO dto, Destinations origin, Destinations destination, List<Tickets> tickets) {
        return Flights.builder()
                .id(dto.getId())
                .origin(origin)
                .destination(destination)
                .date_of_completion(dto.getDate_of_completion())
                .departure_date(dto.getDeparture_date())
                .return_date(dto.getReturn_date())
                .totalSeats(dto.getTotalSeats())
                .availableSeats(dto.getAvailableSeats())
                .tickets(tickets)
                .build();
    }
}
