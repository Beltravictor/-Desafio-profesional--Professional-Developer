package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.entities.Destinations_Category;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.IDestinations_CategoryRepository;
import com.dh.VuelosDH.service.IDestinations_CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IDestinations_CategoryServiceImpl implements IDestinations_CategoryService {
    private final IDestinations_CategoryRepository destinations_categoryRepository;

    @Override
    public Destinations_Category save(Destinations_Category destination_category) {
        return destinations_categoryRepository.save(destination_category);
    }

    @Override
    public Optional<Destinations_Category> findById(Long id) {
        return destinations_categoryRepository.findById(id);
    }

    @Override
    public void update(Destinations_Category destination_category) {
        destinations_categoryRepository.save(destination_category);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Destinations_Category> destinationsToLookFor = findById(id);
        if (destinationsToLookFor.isPresent())
            destinations_categoryRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar el destino con id: " + id);
    }

    @Override
    public List<Destinations_Category> findAll() {
        return destinations_categoryRepository.findAll();
    }
}
