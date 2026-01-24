package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.entities.*;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.DestinationsMapper;
import com.dh.VuelosDH.repository.*;
import com.dh.VuelosDH.service.IDestinationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IDestinationsServiceImpl implements IDestinationsService {

    private final DestinationsMapper destinationsMapper;

    private final IDestinationsRepository iDestinationsRepository;
    private final ICategoryRepository iCategoryRepository;
    private final ICharacteristicsRepository iCharacteristicsRepository;
    private final IFlightsRepository iFlightsRepository;

    @Override
    public DestinationsDTO save(DestinationsDTO dto) throws Exception {

        List<Category> categories = iCategoryRepository.findAllById(dto.getCategories());
        if (categories.size() != dto.getCategories().size()) {
            throw new ResourceNotFoundException("Alguna categoría no existe");
        }

        List<Characteristics> characteristics = iCharacteristicsRepository.findAllById(dto.getCharacteristics());
        if (characteristics.size() != dto.getCharacteristics().size()) {
            throw new ResourceNotFoundException("Alguna característica no existe");
        }

        Destinations des = destinationsMapper.toDes(dto, categories, characteristics);
        iDestinationsRepository.save(des);

        return destinationsMapper.toDto(des);
    }

    @Override
    public DestinationsDTO findById(Long id) throws ResourceNotFoundException {
        Destinations destination = iDestinationsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el Destino con id: " + id)
                );

        return destinationsMapper.toDto(destination);
    }

    @Override
    public DestinationsDTO update(DestinationsDTO dto) throws ResourceNotFoundException {

        iDestinationsRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el Destino con id: " + dto.getId()));

        List<Category> categories = iCategoryRepository.findAllById(dto.getCategories());
        if (categories.size() != dto.getCategories().size()) {
            throw new ResourceNotFoundException("Alguna categoría no existe");
        }

        List<Characteristics> characteristics = iCharacteristicsRepository.findAllById(dto.getCharacteristics());
        if (characteristics.size() != dto.getCharacteristics().size()) {
            throw new ResourceNotFoundException("Alguna característica no existe");
        }

        Destinations des = destinationsMapper.toDes(dto, categories, characteristics);
        iDestinationsRepository.save(des);

        return destinationsMapper.toDto(des);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<Destinations> destinationsToLookFor = iDestinationsRepository.findById(id);
        if (destinationsToLookFor.isPresent())
            iDestinationsRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar el destino con id: " + id);
    }

    @Override
    public List<DestinationsDTO> findAll() {
        List<Destinations> destinations = iDestinationsRepository.findAll();
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations)
            destinationsDTO.add(destinationsMapper.toDto(des));
        return destinationsDTO;
    }

    @Override
    public List<DestinationsDTO> randomsDestinations(Long nro) {
        List<Destinations> destinations = iDestinationsRepository.randomsDestinations(nro);
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations)
            destinationsDTO.add(destinationsMapper.toDto(des));
        return destinationsDTO;
    }

    @Override
    public List<DestinationsDTO> findByCategory(Long id) {
        List<Destinations> destinations = iDestinationsRepository.findByCategory(id);
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations)
            destinationsDTO.add(destinationsMapper.toDto(des));
        return destinationsDTO;
    }

    @Override
    public DestinationsDTO findByName(String name) throws ResourceNotFoundException {
        Destinations destination = iDestinationsRepository.findByName(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el Destino con nombre: " + name)
                );
        return destinationsMapper.toDto(destination);
    }

    @Override
    public List<DestinationsDTO> destinationsByFlight(Long id) throws ResourceNotFoundException {
        List<DestinationsDTO> destinationsDTOS = new ArrayList<>();
        Flights flights = iFlightsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el Vuelo con id: " + id)
                );
        Destinations origen = iDestinationsRepository.findById(flights.getOrigin().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el Destino con id: " + id)
                );
        Destinations destino = iDestinationsRepository.findById(flights.getDestination().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el Destino con id: " + id)
                );
        destinationsDTOS.add(destinationsMapper.toDto(origen));
        destinationsDTOS.add(destinationsMapper.toDto(destino));
        return destinationsDTOS;
    }
}
