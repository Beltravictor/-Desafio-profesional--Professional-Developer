package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;

public interface IDestinationsService {
    DestinationsDTO save (DestinationsDTO destinationsDTO) throws Exception;
    DestinationsDTO findById(Long id) throws ResourceNotFoundException;
    DestinationsDTO update(DestinationsDTO destinationsDTO) throws ResourceNotFoundException;
    void deleteById(Long id) throws ResourceNotFoundException;
    List<DestinationsDTO> findAll();
    List<DestinationsDTO> randomsDestinations(Long nro);
    List<DestinationsDTO> findByCategory(Long id);
    DestinationsDTO findByName(String name) throws ResourceNotFoundException;
    List<DestinationsDTO> destinationsByFlight(Long id) throws ResourceNotFoundException;
}
