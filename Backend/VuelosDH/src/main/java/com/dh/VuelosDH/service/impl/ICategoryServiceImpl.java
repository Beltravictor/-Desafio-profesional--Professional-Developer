package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.CategoryDTO;
import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.entities.Category;
import com.dh.VuelosDH.entities.Destinations_Category;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.ICategoryRepository;
import com.dh.VuelosDH.repository.IDestinations_CategoryRepository;
import com.dh.VuelosDH.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ICategoryServiceImpl implements ICategoryService {

    private ICategoryRepository iCategoryRepository;

    @Autowired
    public ICategoryServiceImpl(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

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
    public Optional<CategoryDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Category> category = iCategoryRepository.findById(id);
        if (category.isPresent()) {
            Category cat = category.get();
            CategoryDTO aux = new CategoryDTO();
            aux.setId(cat.getId());
            aux.setName(cat.getName());
            aux.setUrl(cat.getUrl());
            return Optional.of(aux);
        } else
            throw new ResourceNotFoundException("No se puedo eliminar la Imagen con id: " + id);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setUrl(categoryDTO.getUrl());

        iCategoryRepository.save(category);
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
