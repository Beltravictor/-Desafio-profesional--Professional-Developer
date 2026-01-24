package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservationsController {

    private final IReservationService iReservationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationsAdminDTO> save(@RequestBody ReservationsAdminDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iReservationService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationsAdminDTO>> findAll() {
        return ResponseEntity.ok(iReservationService.findAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationsAdminDTO> update(@RequestBody ReservationsAdminDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iReservationService.update(dto));
    }

}
