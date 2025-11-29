package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.CategoryDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    private final ICategoryService iCategoryService;

    @Autowired
    public CategoryController(ICategoryService iCategoryService) {
        this.iCategoryService = iCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(iCategoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(iCategoryService.save(categoryDTO));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody CategoryDTO categoryDTO) throws ResourceNotFoundException {
        ResponseEntity<String> response;
        Optional<CategoryDTO> destinationsDTOToLookFor = iCategoryService.findById(categoryDTO.getId());
        if (destinationsDTOToLookFor.isPresent()) {
            iCategoryService.update(categoryDTO);
            response = ResponseEntity.ok("Se actualizo el Destino con éxito");
        } else {
            response = ResponseEntity.ok("No se pudo actualizar el Destino");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        iCategoryService.deleteById(id);
        return ResponseEntity.ok("Se elimino la Categoría con éxito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<CategoryDTO> categoryDTO = iCategoryService.findById(id);

        if (categoryDTO.isPresent())
            return ResponseEntity.ok(categoryDTO.get());
        else
            return ResponseEntity.notFound().build();
    }
}
