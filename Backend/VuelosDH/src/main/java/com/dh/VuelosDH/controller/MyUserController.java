package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.*;
import com.dh.VuelosDH.service.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myuser")
public class MyUserController {

    private final IMyUserService iMyUserService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        return ResponseEntity.ok(iMyUserService.getProfile(authentication.getName()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMyUser(Authentication authentication){
        iMyUserService.deleteMyUser(authentication.getName());
        return ResponseEntity.ok("Se elimino su usuario con éxito");
    }

    @PutMapping("/addfavorite/{id}")
    public ResponseEntity<String> addFavorite(Authentication authentication, @PathVariable Long id) {
        return iMyUserService.addFavorite(authentication.getName(), id);
    }

    @PutMapping("/removefavorite/{id}")
    public ResponseEntity<String> removeFavorite(Authentication authentication, @PathVariable Long id) {
        return iMyUserService.removeFavorite(authentication.getName(), id);
    }



    @PostMapping("/reserva")
    public ResponseEntity<ReservationsDTO> saveMyReservation(@RequestBody ReservationsDTO dto, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.saveMyReservation(dto, authentication.getName()));
    }

    @GetMapping("/reserva/{id}")
    public ResponseEntity<ReservationsDTO> findMyReservationById(@PathVariable Long id, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyReservationById(id, authentication.getName()));
    }

    @GetMapping("/reserva")
    public ResponseEntity<List<ReservationsDTO>> findMyReservations(Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyReservations(authentication.getName()));
    }

    @PutMapping("/reserva")
    public ResponseEntity<ReservationsDTO> updateMyReservation(@RequestBody ReservationsDTO dto, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.updateMyReservation(dto, authentication.getName()));
    }

    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<String> deleteMyReservationById(@PathVariable Long id, Authentication authentication) throws ResponseStatusException {
        iMyUserService.deleteMyReservation(id, authentication.getName());
        return ResponseEntity.ok("Reserva eliminada con éxito");
    }


    @PostMapping("/pasajero")
    public ResponseEntity<PassengersDTO> saveMyPassenger(@RequestBody PassengersDTO dto, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.saveMyPassenger(dto, authentication.getName()));
    }

    @GetMapping("/pasajero/{id}")
    public ResponseEntity<PassengersDTO> findMyPassengerById(@PathVariable Long id, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyPassengerById(id, authentication.getName()));
    }

    @GetMapping("/pasajero")
    public ResponseEntity<List<PassengersDTO>> findMyPassengers(Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyPassengers(authentication.getName()));
    }

    @PutMapping("/pasajero")
    public ResponseEntity<PassengersDTO> updateMyPassenger(@RequestBody PassengersDTO dto, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.updateMyPassenger(dto, authentication.getName()));
    }

    @DeleteMapping("/pasajero/{id}")
    public ResponseEntity<String> deleteMyPassengerById(@PathVariable Long id, Authentication authentication) throws ResponseStatusException {
        iMyUserService.deleteMyPassenger(id, authentication.getName());
        return ResponseEntity.ok("Pasajero eliminada con éxito");
    }



    @GetMapping("/ticket/{id}")
    public ResponseEntity<TicketsDTO> findMyTicketById(@PathVariable Long id, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyTicketById(id, authentication.getName()));
    }

    @GetMapping("/ticket")
    public ResponseEntity<List<TicketsDTO>> findMyTickets(Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyTickets(authentication.getName()));
    }

    @PutMapping("/ticket")
    public ResponseEntity<TicketsDTO> updateMyTicket(@RequestBody TicketsDTO dto, Authentication authentication) throws ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.updateMyTicket(dto, authentication.getName()));
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<String> deleteMyTicketById(@PathVariable Long id, Authentication authentication) throws ResponseStatusException {
        iMyUserService.deleteMyTicket(id, authentication.getName());
        return ResponseEntity.ok("Ticket eliminado con éxito");
    }

    @GetMapping("/favoritos")
    public ResponseEntity<List<DestinationsDTO>> findMyFavoritesDestinations(Authentication authentication) throws  ResponseStatusException {
        return ResponseEntity.ok(iMyUserService.findMyFavoritesDestinations(authentication.getName()));
    }
}
