package com.dh.VuelosDH.service;

import com.dh.VuelosDH.entities.Destinations_Category;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IDestinations_CategoryService {

    Destinations_Category save (Destinations_Category destinations_category);
    Optional<Destinations_Category> findById(Long id);
    void update(Destinations_Category destinations_category);
    void delete(Long id) throws ResourceNotFoundException;
    List<Destinations_Category> findAll();
}

