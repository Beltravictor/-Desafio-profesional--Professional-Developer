package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.PassengersAdminDTO;
import com.dh.VuelosDH.dto.PassengersDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IPassengersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pasajeros")
@RequiredArgsConstructor
public class PassengersController {

    private final IPassengersService iPassengersService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassengersAdminDTO> save(@RequestBody PassengersAdminDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iPassengersService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PassengersAdminDTO>> findAll() {
        return ResponseEntity.ok(iPassengersService.findAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassengersAdminDTO> update(@RequestBody PassengersAdminDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iPassengersService.update(dto));
    }
}
