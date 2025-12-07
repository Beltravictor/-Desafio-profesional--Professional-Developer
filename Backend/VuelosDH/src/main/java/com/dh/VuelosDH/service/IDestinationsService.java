package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IDestinationsService {
    DestinationsDTO save (DestinationsDTO destinationsDTO);
    ResponseEntity<DestinationsDTO> findById(Long id) throws ResourceNotFoundException;
    ResponseEntity<String> update(DestinationsDTO destinationsDTO);
    void deleteById(Long id) throws ResourceNotFoundException;
    List<DestinationsDTO> findAll();
    List<DestinationsDTO> randomsDestinations(Long nro);
    List<DestinationsDTO> findByCategory(Long id);
    ResponseEntity<DestinationsDTO> findByName(String name) throws ResourceNotFoundException;
}
