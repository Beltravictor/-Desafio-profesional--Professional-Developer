package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.CategoryDTO;
import com.dh.VuelosDH.entities.Category;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.ICategoryRepository;
import com.dh.VuelosDH.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ICategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository iCategoryRepository;

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = new Category();
        CategoryDTO dto = new CategoryDTO();

        category.setName(categoryDTO.getName());
        category.setUrl(categoryDTO.getUrl());
        iCategoryRepository.save(category);

        dto.setName(categoryDTO.getName());
        dto.setId(category.getId());
        dto.setUrl(categoryDTO.getUrl());
        return dto;
    }

    @Override
    public ResponseEntity<CategoryDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Category> category = iCategoryRepository.findById(id);
        if (category.isPresent()) {
            Category cat = category.get();
            CategoryDTO aux = new CategoryDTO();
            aux.setId(cat.getId());
            aux.setName(cat.getName());
            aux.setUrl(cat.getUrl());
            return ResponseEntity.ok(aux);
        } else
            throw new ResourceNotFoundException("No se puedo eliminar la Categoría con id: " + id);
    }

    @Override
    public ResponseEntity<String> update(CategoryDTO categoryDTO) {
        Optional<Category> categoryToLookFor = iCategoryRepository.findById(categoryDTO.getId());
        if (categoryToLookFor.isPresent()) {
            Category category = new Category();

            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setUrl(categoryDTO.getUrl());

            iCategoryRepository.save(category);
            return ResponseEntity.ok("Se actualizo la categoría con éxito");
        } else
            return ResponseEntity.ok("No se pudo actualizar la categoría");

    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<Category> categoryToLookFor = iCategoryRepository.findById(id);
        if (categoryToLookFor.isPresent())
            iCategoryRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar la categoría con id: " + id);
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = iCategoryRepository.findAll();
        List<CategoryDTO> dto = new ArrayList<>();
        for (Category cat : categories) {
            CategoryDTO aux = new CategoryDTO();
            aux.setId(cat.getId());
            aux.setName(cat.getName());
            aux.setUrl(cat.getUrl());
            dto.add(aux);
        }
        return dto;
    }
}
