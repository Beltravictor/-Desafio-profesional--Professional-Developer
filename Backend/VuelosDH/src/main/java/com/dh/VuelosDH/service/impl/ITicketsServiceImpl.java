package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.dto.TicketsDTO;
import com.dh.VuelosDH.entities.*;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.TicketsMapper;
import com.dh.VuelosDH.repository.*;
import com.dh.VuelosDH.service.ITicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ITicketsServiceImpl implements ITicketsService {

    private final ITicketsRepository iTicketsRepository;
    private final IFlightsRepository iFlightsRepository;
    private final IReservationsRepository iReservationsRepository;
    private final IPassengersRepository iPassengersRepository;
    private final IUserRepository iUserRepository;
    private final TicketsMapper ticketsMapper;

    @Override
    public TicketsDTO save(TicketsDTO dto) throws ResourceNotFoundException {

        Flights flight = iFlightsRepository.findById(dto.getFlight_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo"));

        Reservations reservation = iReservationsRepository.findById(dto.getReservation_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró la reserva"));


        Tickets ticket = ticketsMapper.toTicket(dto);
        ticket.setCreationDate(Calendar.getInstance().getTime());
        Tickets saved = iTicketsRepository.save(ticket);
        flight.addTicket(ticket);
        reservation.addTicket(ticket);
        return ticketsMapper.toDto(saved);
    }

    @Override
    public TicketsDTO findById(Long id) throws ResourceNotFoundException {
        Tickets ticket = iTicketsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el ticket"));

        return ticketsMapper.toDto(ticket);
    }

    @Override
    @Transactional
    public TicketsDTO update(TicketsDTO dto) throws ResourceNotFoundException {
        iTicketsRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el ticket"));

        iReservationsRepository.findById(dto.getReservation_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró la reserva"));

        Passengers passenger = iPassengersRepository.findById(dto.getPassenger_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el pasajero"));


        Tickets saved = iTicketsRepository.save(ticketsMapper.toTicket(dto));
        passenger.addTicket(saved);
        return ticketsMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        iTicketsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el ticket"));

        iTicketsRepository.deleteById(id);
    }

    @Override
    public List<TicketsDTO> findAll() {
        List<TicketsDTO> ticketsDTOS = new ArrayList<>();
        List<Tickets> tickets = iTicketsRepository.findAll();
        for (Tickets ticket : tickets) {
            ticketsDTOS.add(ticketsMapper.toDto(ticket));
        }
        return ticketsDTOS;
    }

    @Override
    @Transactional
    public List<TicketsDTO> createTickets(ReservationsAdminDTO dto) throws ResponseStatusException {

        var user = iUserRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        Reservations reservation = user.getReservations().stream()
                .filter(r -> r.getId().equals(dto.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        if (!reservation.isPaid()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se pueden crear tickets para una reserva no pagada"
            );
        }

        Flights startFlight = iFlightsRepository.findById(reservation.getStartFlight().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo"));

        Map<Passengers_Classes, Integer> classes = Map.of(
                Passengers_Classes.BUSINESS, reservation.getBusinessClass(),
                Passengers_Classes.ECONOMY, reservation.getEconomyClass(),
                Passengers_Classes.PREMIUM_ECONOMY, reservation.getPremium_economyClass(),
                Passengers_Classes.FIRST, reservation.getFirstClass()
        );

        List<TicketsDTO> tickets = new ArrayList<>();

        classes.forEach((ticketClass, amount) ->
                createStartTicket(ticketClass, amount, reservation, startFlight, tickets)
        );

        if(reservation.getReturnFlight() != null) {
            Flights returnFlight = iFlightsRepository.findById(reservation.getReturnFlight().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo"));

            classes.forEach((ticketClass, amount) ->
                    createReturnTicket(ticketClass, amount, reservation, returnFlight, tickets)
            );
        }


        reservation.setReservationStatus(Status.TICKETED);

        return tickets;
    }

    private void createStartTicket(Passengers_Classes pc,
                              Integer amount,
                              Reservations res,
                              Flights flight,
                              List<TicketsDTO> list) {

        for (int i = 0; i < amount; i++) {
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            Tickets ticket = Tickets.builder()
                    .seatNumber(flight.getAvailableSeats())
                    .status(Status.CREATED)
                    .passengersClasses(pc)
                    .creationDate(Calendar.getInstance().getTime())
                    .build();
            iTicketsRepository.save(ticket);
            flight.addTicket(ticket);
            flight.addStartFlight(res);
            res.addTicket(ticket);
            list.add(ticketsMapper.toDto(ticket));
        }

    }

    private void createReturnTicket(Passengers_Classes pc,
                                   Integer amount,
                                   Reservations res,
                                   Flights flight,
                                   List<TicketsDTO> list) {

        for (int i = 0; i < amount; i++) {
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            Tickets ticket = Tickets.builder()
                    .seatNumber(flight.getAvailableSeats())
                    .status(Status.CREATED)
                    .passengersClasses(pc)
                    .creationDate(Calendar.getInstance().getTime())
                    .build();
            iTicketsRepository.save(ticket);
            flight.addTicket(ticket);
            flight.addStartFlight(res);
            res.addTicket(ticket);
            list.add(ticketsMapper.toDto(ticket));
        }

    }

}
