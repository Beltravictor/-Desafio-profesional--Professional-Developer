package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IDestinationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinos")
public class DestinationsController {

    private IDestinationsService iDestinationsService;

    @Autowired
    public DestinationsController(IDestinationsService iDestinationsService) {
        this.iDestinationsService = iDestinationsService;
    }

    @PostMapping
    public ResponseEntity<DestinationsDTO> save(@RequestBody DestinationsDTO destinationsDTO) {
        return ResponseEntity.ok(iDestinationsService.save(destinationsDTO));
    }

    @GetMapping
    public ResponseEntity<List<DestinationsDTO>> findAll() {
        return ResponseEntity.ok(iDestinationsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationsDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<DestinationsDTO> destiantionDTO = iDestinationsService.findById(id);

        if (destiantionDTO.isPresent())
            return ResponseEntity.ok(destiantionDTO.get());
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        iDestinationsService.deleteById(id);
        return ResponseEntity.ok("Se elimino el Destino con éxito");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody DestinationsDTO destinationsDTO) throws ResourceNotFoundException {
        ResponseEntity<String> response;
        Optional<DestinationsDTO> destinationsDTOToLookFor = iDestinationsService.findById(destinationsDTO.getId());
        if (destinationsDTOToLookFor.isPresent()){
            iDestinationsService.update(destinationsDTO);
            response = ResponseEntity.ok("Se actualizo el Destino con éxito");
        } else {
            response = ResponseEntity.ok("No se pudo actualizar el Destino");
        }
        return response;
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
        Optional<DestinationsDTO> destiantionDTO = iDestinationsService.findByName(name);
        if (destiantionDTO.isPresent())
            return ResponseEntity.ok(destiantionDTO.get());
        else
            return ResponseEntity.notFound().build();
    }
}
