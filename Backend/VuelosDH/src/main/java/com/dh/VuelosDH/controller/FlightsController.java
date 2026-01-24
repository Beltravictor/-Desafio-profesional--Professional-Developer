package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.FlightsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IFlightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vuelos")
@RequiredArgsConstructor
public class FlightsController {

    private final IFlightsService iFlightsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightsDTO> save(@RequestBody FlightsDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iFlightsService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<FlightsDTO>> findAll() {
        return ResponseEntity.ok(iFlightsService.findAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightsDTO> update(@RequestBody FlightsDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(iFlightsService.update(dto));
    }

    @GetMapping("/destinos/{ori}/{des}")
    public ResponseEntity<List<FlightsDTO>> findAllByOriginDestination(@PathVariable Long ori, @PathVariable Long des) {
        return ResponseEntity.ok(iFlightsService.findAllByOriginDestination(ori, des));
    }
}
