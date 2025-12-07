package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.ImagesDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IImagesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imagenes")
public class ImagesController {

    private final IImagesService iImagesService;

    @Autowired
    public ImagesController(IImagesService iImagesService) {
        this.iImagesService = iImagesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagesDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        return iImagesService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<ImagesDTO>> findAll() {
        return ResponseEntity.ok(iImagesService.findAll());
    }

    @PostMapping
    public ResponseEntity<ImagesDTO> save(@RequestBody @Valid  ImagesDTO imagesDTO) {
        return ResponseEntity.ok(iImagesService.save(imagesDTO));
    }
}
