package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.CharacteristicsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.ICharacteristicsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caracteristicas")
@RequiredArgsConstructor
public class CharacteristicsController {

    private final ICharacteristicsService iCharacteristicsService;

    @GetMapping
    public ResponseEntity<List<CharacteristicsDTO>> findAll() {
        return ResponseEntity.ok(iCharacteristicsService.findAll());
    }

    @PostMapping
    public ResponseEntity<CharacteristicsDTO> save(@RequestBody @Valid CharacteristicsDTO characteristicsDTO) {
        return ResponseEntity.ok(iCharacteristicsService.save(characteristicsDTO));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@RequestBody @Valid CharacteristicsDTO characteristicsDTO) throws ResourceNotFoundException {
        return iCharacteristicsService.update(characteristicsDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        iCharacteristicsService.deleteById(id);
        return ResponseEntity.ok("Se elimino la Categoría con éxito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacteristicsDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        return iCharacteristicsService.findById(id);
    }
}
