package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IDestinationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinos")
@RequiredArgsConstructor
public class DestinationsController {

    private final IDestinationsService iDestinationsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DestinationsDTO> save(@RequestBody DestinationsDTO destinationsDTO) throws Exception {
        return ResponseEntity.ok(iDestinationsService.save(destinationsDTO));
    }

    @GetMapping
    public ResponseEntity<List<DestinationsDTO>> findAll() {
        return ResponseEntity.ok(iDestinationsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationsDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(iDestinationsService.findById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        iDestinationsService.deleteById(id);
        return ResponseEntity.ok("Se elimino el Destino con éxito");
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DestinationsDTO> update(@RequestBody DestinationsDTO destinationsDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(iDestinationsService.update(destinationsDTO));
    }

    @GetMapping("/random/{nro}")
    public ResponseEntity<List<DestinationsDTO>> randomDestinations(@PathVariable Long nro) {
        return ResponseEntity.ok(iDestinationsService.randomsDestinations(nro));
    }

    @GetMapping("/categoria/{nro}")
    public ResponseEntity<List<DestinationsDTO>> findByCategory(@PathVariable Long nro) {
        return ResponseEntity.ok(iDestinationsService.findByCategory(nro));
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<DestinationsDTO> findByName(@PathVariable String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(iDestinationsService.findByName(name));
    }

    @GetMapping("/vuelo/{id}")
    public ResponseEntity<List<DestinationsDTO>> destinationsByFlight(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(iDestinationsService.destinationsByFlight(id));
    }
}
