package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.FlightsDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.Flights;
import com.dh.VuelosDH.entities.Tickets;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.FlightsMapper;
import com.dh.VuelosDH.repository.IDestinationsRepository;
import com.dh.VuelosDH.repository.IFlightsRepository;
import com.dh.VuelosDH.repository.ITicketsRepository;
import com.dh.VuelosDH.service.IFlightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IFlightsServiceImpl implements IFlightsService {

    private final IFlightsRepository iFlightsRepository;
    private final IDestinationsRepository iDestinationsRepository;
    private final ITicketsRepository iTicketsRepository;
    private final FlightsMapper flightsMapper;

    @Override
    public FlightsDTO save(FlightsDTO dto) throws ResourceNotFoundException {

        Destinations origin = iDestinationsRepository.findById(dto.getOrigin())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el destino origen")
                );

        Destinations destination = iDestinationsRepository.findById(dto.getOrigin())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el destino final")
                );

        List<Tickets> tickets = iTicketsRepository.findAllById(dto.getTickets());
        if (tickets.size() != dto.getTickets().size()) {
            throw new ResourceNotFoundException("Algún ticket no existe");
        }

        Flights flights = flightsMapper.toFlight(dto, origin, destination, tickets);
        flights.setDate_of_completion(Calendar.getInstance().getTime());
        Flights saved = iFlightsRepository.save(flights);

        return flightsMapper.toDto(saved);
    }

    @Override
    public FlightsDTO findById(Long id) throws ResourceNotFoundException {
        Flights flights = iFlightsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el vuelo")
                );

        return  flightsMapper.toDto(flights);
    }

    @Override
    public FlightsDTO update(FlightsDTO dto) throws ResourceNotFoundException {
        iFlightsRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el vuelo")
                );

        Destinations origin = iDestinationsRepository.findById(dto.getOrigin())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el destino origen")
                );

        Destinations destination = iDestinationsRepository.findById(dto.getOrigin())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el destino final")
                );

        List<Tickets> tickets = iTicketsRepository.findAllById(dto.getTickets());
        if (tickets.size() != dto.getTickets().size()) {
            throw new ResourceNotFoundException("Algún ticket no existe");
        }

        Flights saved = iFlightsRepository.save(flightsMapper.toFlight(dto, origin, destination, tickets));

        return flightsMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        iFlightsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el vuelo")
                );
        iFlightsRepository.deleteById(id);
    }

    @Override
    public List<FlightsDTO> findAll() {
        List<FlightsDTO> flightsDTOS = new ArrayList<>();
        List<Flights> flightsList = iFlightsRepository.findAll();
        for (Flights flights : flightsList)
            flightsDTOS.add(flightsMapper.toDto(flights));
        return flightsDTOS;
    }

    @Override
    public List<FlightsDTO> findAllByOriginDestination(Long ori, Long des) {
        List<Flights> flightsList = iFlightsRepository.findAllByOriginDestination(ori, des);
        List<Flights> flightsListInverted = iFlightsRepository.findAllByOriginDestination(des, ori);
        List<FlightsDTO> listToReturn = new ArrayList<>();
        for (Flights flight : flightsList) {
            listToReturn.add(flightsMapper.toDto(flight));
        }
        for (Flights flight : flightsListInverted) {
            FlightsDTO aux = flightsMapper.toDto(flight);
            Date dateAux = aux.getDeparture_date();
            aux.setDeparture_date(aux.getReturn_date());
            aux.setReturn_date(dateAux);
            listToReturn.add(aux);
        }
        return listToReturn;
    }
}
