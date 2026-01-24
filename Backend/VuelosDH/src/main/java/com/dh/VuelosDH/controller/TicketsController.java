package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.dto.TicketsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.ITicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketsController {

    private final ITicketsService iTicketsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketsDTO> save(@RequestBody TicketsDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iTicketsService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TicketsDTO>> findAll() {
        return ResponseEntity.ok(iTicketsService.findAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketsDTO> update(@RequestBody TicketsDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iTicketsService.update(dto));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TicketsDTO>> createTickets(@RequestBody ReservationsAdminDTO dto) throws ResponseStatusException {
        return ResponseEntity.ok(iTicketsService.createTickets(dto));
    }
}
