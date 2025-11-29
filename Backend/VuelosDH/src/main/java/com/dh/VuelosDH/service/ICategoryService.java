package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.CategoryDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    CategoryDTO save (CategoryDTO categoryDTO);
    Optional<CategoryDTO> findById(Long id) throws ResourceNotFoundException;
    void update(CategoryDTO categoryDTO);
    void deleteById(Long id) throws ResourceNotFoundException;
    List<CategoryDTO> findAll();

}
