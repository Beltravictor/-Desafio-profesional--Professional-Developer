package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

public interface IMyUserService {

    public UserDTO getProfile(String email);
    public void deleteMyUser(String email) throws ResponseStatusException;
    public ResponseEntity<String> addFavorite(String email, Long id) throws ResponseStatusException;
    public ResponseEntity<String> removeFavorite(String email, Long id) throws ResponseStatusException;

    ReservationsDTO saveMyReservation(ReservationsDTO dto, String email) throws ResponseStatusException;
    ReservationsDTO findMyReservationById(Long id, String email) throws ResponseStatusException;
    List<ReservationsDTO> findMyReservations(String email) throws ResponseStatusException;
    ReservationsDTO updateMyReservation(ReservationsDTO dto, String email) throws ResponseStatusException;
    void deleteMyReservation(Long id, String email) throws ResponseStatusException;

    PassengersDTO saveMyPassenger(PassengersDTO dto, String email) throws ResponseStatusException;
    PassengersDTO findMyPassengerById(Long id, String email) throws ResponseStatusException;
    List<PassengersDTO> findMyPassengers(String email) throws ResponseStatusException;
    PassengersDTO updateMyPassenger(PassengersDTO dto, String email) throws ResponseStatusException;
    void deleteMyPassenger(Long id, String email) throws ResponseStatusException;

    TicketsDTO findMyTicketById(Long id, String email) throws ResponseStatusException;
    List<TicketsDTO> findMyTickets(String email);
    TicketsDTO updateMyTicket(TicketsDTO dto, String email) throws ResponseStatusException;
    void deleteMyTicket(Long id, String email) throws ResponseStatusException;

    List<DestinationsDTO> findMyFavoritesDestinations(String email);
}
