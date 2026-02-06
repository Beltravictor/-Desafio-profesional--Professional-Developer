package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.*;
import com.dh.VuelosDH.entities.*;
import com.dh.VuelosDH.mapper.*;
import com.dh.VuelosDH.repository.*;
import com.dh.VuelosDH.service.EmailService;
import com.dh.VuelosDH.service.IMyUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IMyUserServiceImpl implements IMyUserService {

    private final IDestinationsRepository iDestinationsRepository;
    private final IReservationsRepository iReservationsRepository;
    private final IPassengersRepository iPassengersRepository;
    private final IUserRepository iUserRepository;
    private final ITicketsRepository iTicketsRepository;
    private final IFlightsRepository iFlightsRepository;
    private final IUserReviewsRepository iUserReviewsRepository;
    private final EmailService emailService;

    private final ReservationsMapper reservationsMapper;
    private final PassengersMapper passengersMapper;
    private final TicketsMapper ticketsMapper;
    private final UserMapper userMapper;
    private final DestinationsMapper destinationsMapper;
    private final UserReviewsMapper userReviewsMapper;
    private final FlightsMapper flightsMapper;

    @Override
    public UserDTO getProfile(String email) {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return userMapper.toDto(user);
    }

    @Override
    public void deleteMyUser(String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        iUserRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public ResponseEntity<String> addFavorite(String email, Long id) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        var destination = iDestinationsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destino no encontrado"));

        if (!user.getFavorites().add(destination)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El destino ya se encuentra en favoritos"
            );
        }

        return ResponseEntity.ok("Se agregó el destino favorito con éxito");
    }

    @Override
    @Transactional
    public ResponseEntity<String> removeFavorite(String email, Long id) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        var destination = iDestinationsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destino no encontrado"));

        if (!user.getFavorites().remove(destination)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El destino no se encuentra en favoritos"
            );
        }
        return ResponseEntity.ok("Se eliminó el destino favorito con éxito");
    }

    // --------------------------------------------------------------------------------------------------------

    @Override
    @Transactional
    public ReservationsDTO saveMyReservation(ReservationsDTO dto, String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        Flights startFlight = iFlightsRepository.findById(dto.getStartFlight())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo de ida"));

        Reservations res = reservationsMapper.toRes(dto);
        res.setCreationDate(Calendar.getInstance().getTime());
        res.setTickets(new ArrayList<>());
        res.setReservationStatus(Status.CREATED);
        user.addReservation(res);
        startFlight.addStartFlight(res);

        if (dto.getReturnFlight() != null) {
            Flights returnFlight = iFlightsRepository.findById(dto.getReturnFlight())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo de vuelta"));
            returnFlight.addReturnFlight(res);
        }
        Reservations savedRes = iReservationsRepository.save(res);

        emailService.sendReservationEmail(email, savedRes);

        return reservationsMapper.toDto(savedRes);
    }

    @Override
    public ReservationsDTO findMyReservationById(Long id, String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        Reservations res = user.getReservations().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva no encontrada para el usuario"));

        return reservationsMapper.toDto(res);
    }

    @Override
    public List<ReservationsDTO> findMyReservations(String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        List<ReservationsDTO> reservationsToReturn = new ArrayList<>();
        for (Reservations res : user.getReservations()) {
            reservationsToReturn.add(reservationsMapper.toDto(res));
        }

        return reservationsToReturn;
    }

    @Override
    @Transactional
    public ReservationsDTO updateMyReservation(ReservationsDTO dto, String email) throws ResponseStatusException {

        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        Reservations reservations = user.getReservations().stream()
                .filter(r -> r.getId().equals(dto.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva no encontrada para el usuario"));

        if (reservations.getReservationStatus() != Status.CREATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se pueden editar tickets para una reserva no pagada"
            );
        }

        Flights startFlight = iFlightsRepository.findById(dto.getStartFlight())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo de ida"));

        Reservations res = reservationsMapper.toRes(dto);
        res.setId(dto.getId());
        res.setCreationDate(reservations.getCreationDate());
        res.setTickets(reservations.getTickets());
        startFlight.addStartFlight(res);
        if (dto.getReturnFlight() != null) {
            Flights returnFlight = iFlightsRepository.findById(dto.getReturnFlight())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo de vuelta"));
            returnFlight.addReturnFlight(res);
        }
        user.removeReservation(reservations);
        user.addReservation(res);

        iUserRepository.save(user);
        return reservationsMapper.toDto(res);
    }

    @Override
    public void deleteMyReservation(Long id, String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        Reservations res = user.getReservations().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva no encontrada para el usuario"));

        Flights startFlight = iFlightsRepository.findById(res.getStartFlight().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo"));

        startFlight.removeStartFlight(res);

        if (res.getReturnFlight() != null) {
            Flights returnFlight = iFlightsRepository.findById(res.getReturnFlight().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el vuelo de vuelta"));
            returnFlight.removeReturnFlight(res);
        }
        user.removeReservation(res);
        iUserRepository.save(user);
    }

    // --------------------------------------------------------------------------------------------------------


    @Override
    public PassengersDTO saveMyPassenger(PassengersDTO dto, String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        Passengers pass = passengersMapper.toPass(dto);
        pass.setTickets(new ArrayList<>());
        user.addPassenger(pass);
        iUserRepository.save(user);

        return passengersMapper.toDto(pass);
    }

    @Override
    public PassengersDTO findMyPassengerById(Long id, String email) throws ResponseStatusException {

        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        Passengers pass = user.getPassengers().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva no encontrada para el usuario"));
        return passengersMapper.toDto(pass);
    }

    @Override
    public List<PassengersDTO> findMyPassengers(String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        List<PassengersDTO> passengersDTOS = new ArrayList<>();
        for (Passengers pass : user.getPassengers()) {
            passengersDTOS.add(passengersMapper.toDto(pass));
        }

        return passengersDTOS;
    }

    @Override
    @Transactional
    public PassengersDTO updateMyPassenger(PassengersDTO dto, String email) throws ResponseStatusException {
        Passengers passengers = iPassengersRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el Ticket"));
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        Passengers pass = passengersMapper.toPass(dto);
        pass.setId(dto.getId());
        pass.setTickets(passengers.getTickets());
        pass.setUser(user);
        iPassengersRepository.save(pass);

        return passengersMapper.toDto(pass);
    }

    @Override
    public void deleteMyPassenger(Long id, String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        Passengers passenger = user.getPassengers().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        user.removePassenger(passenger);

        iUserRepository.save(user);
    }

    // --------------------------------------------------------------------------------------------------------

    @Override
    @Transactional
    public TicketsDTO findMyTicketById(Long id, String email) throws ResponseStatusException {

        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        Tickets ticket = iTicketsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el Ticket"));

        if (!Objects.equals(ticket.getReservations().getUser(), user)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tiene permisos sobre este ticket"
            );
        }

        return ticketsMapper.toDto(ticket);
    }

    @Override
    @Transactional
    public TicketsDTO updateMyTicket(TicketsDTO dto, String email) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        Tickets ticket = iTicketsRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el Ticket"));

        Reservations reservation = user.getReservations().stream()
                .filter(r -> r.getId().equals(dto.getReservation_id()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        Passengers passenger = user.getPassengers().stream()
                .filter(r -> r.getId().equals(dto.getPassenger_id()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pasajero no encontrada"));

        Tickets saved = iTicketsRepository.save(ticketsMapper.toTicket(dto));
        saved.setReservations(ticket.getReservations());
        saved.setFlight(ticket.getFlight());
        ticket.getPassengers().removeTicket(ticket);
        passenger.addTicket(saved);

        return ticketsMapper.toDto(ticket);
    }

    @Override
    @Transactional
    public void deleteMyTicket(Long id, String email) throws ResponseStatusException {

        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        Tickets ticket = iTicketsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el Ticket"));

        if (!Objects.equals(ticket.getReservations().getUser(), user)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tiene permisos sobre este ticket"
            );
        }

        ticket.getReservations().removeTicket(ticket);
        ticket.getPassengers().removeTicket(ticket);
        ticket.getFlight().removeTicket(ticket);
    }

    @Override
    public List<TicketsDTO> findMyTickets(String email) throws ResponseStatusException {

        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        List<TicketsDTO> ticketsDTOS = new ArrayList<>();

        for (Reservations res : user.getReservations()) {
            for (Tickets tick : res.getTickets()) {
                ticketsDTOS.add(ticketsMapper.toDto(tick));
            }
        }

        return ticketsDTOS;
    }

    @Override
    public List<DestinationsDTO> findMyFavoritesDestinations(String email) {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        UserDTO userDTO = userMapper.toDto(user);

        List<Destinations> destinations = iDestinationsRepository.findAllById(userDTO.getFavorites());

        List<DestinationsDTO> destinationsDTOS = new ArrayList<>();
        for (Destinations des : destinations) {
            destinationsDTOS.add(destinationsMapper.toDto(des));
        }

        return destinationsDTOS;

    }

    // --------------------------------------------------------------------------------------------------------


    @Override
    public List<UserReviewsDTO> findMyReviews(String email) {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        List<UserReviews> reviews = iUserReviewsRepository.findAllById(userMapper.toDto(user).getReviews());
        List<UserReviewsDTO> userReviewsDTOS = new ArrayList<>();
        for (UserReviews userReviews : reviews) {
            userReviewsDTOS.add(userReviewsMapper.toDto(userReviews));
        }
        return userReviewsDTOS;
    }

    @Override
    public UserReviewsDTO createMyReview(String email, UserReviewsDTO dto) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        Destinations destinations = iDestinationsRepository.findById(dto.getDestination_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró el Destino"));

        UserReviews userReview = userReviewsMapper.toEntity(dto);
        userReview.setDestination(destinations);
        userReview.setCreationDate(Calendar.getInstance().getTime());
        userReview.setName(user.getFirstname());
        user.addReview(userReview);
        iUserRepository.save(user);
        return userReviewsMapper.toDto(userReview);
    }

    @Override
    public void deleteMyReview(String email, Long id) throws ResponseStatusException {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        UserReviews userReview = iUserReviewsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error no se encontró la reseña"));

        user.removeReview(userReview);
        iUserRepository.save(user);
    }

    // --------------------------------------------------------------------------------------------------------

    @Override
    public List<FlightsDTO> myReservationsFlights(String email) throws ResponseStatusException{
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario incorrecto"));

        List<FlightsDTO> flightsDTOS = new ArrayList<>();
        for (Reservations res : user.getReservations()) {
            flightsDTOS.add(flightsMapper.toDto(res.getStartFlight()));
            if (res.getReturnFlight() != null)
                flightsDTOS.add(flightsMapper.toDto(res.getReturnFlight()));
        }

        return flightsDTOS;

    }
}
