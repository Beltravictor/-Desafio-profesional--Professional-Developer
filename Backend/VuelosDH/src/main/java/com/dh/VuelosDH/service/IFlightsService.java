package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.FlightsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;

public interface IFlightsService {

    FlightsDTO save (FlightsDTO flightsDTO) throws ResourceNotFoundException;
    FlightsDTO findById(Long id) throws ResourceNotFoundException;
    FlightsDTO update(FlightsDTO flightsDTO) throws ResourceNotFoundException;
    void deleteById(Long id) throws ResourceNotFoundException;
    List<FlightsDTO> findAll();
    List<FlightsDTO> findAllByOriginDestination(Long ori, Long des);
}
