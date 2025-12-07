package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.CategoryDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {

    CategoryDTO save (CategoryDTO categoryDTO);
    ResponseEntity<CategoryDTO> findById(Long id) throws ResourceNotFoundException;
    ResponseEntity<String> update(CategoryDTO categoryDTO);
    void deleteById(Long id) throws ResourceNotFoundException;
    List<CategoryDTO> findAll();
}
