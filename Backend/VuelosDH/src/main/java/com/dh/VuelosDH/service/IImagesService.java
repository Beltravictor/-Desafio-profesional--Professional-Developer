package com.dh.VuelosDH.service;


import com.dh.VuelosDH.dto.ImagesDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IImagesService {

    ImagesDTO save (ImagesDTO imagesDTO);
    ResponseEntity<ImagesDTO> findById(Long id) throws ResourceNotFoundException;
    void update(ImagesDTO imagesDTO);
    void delete(Long id) throws ResourceNotFoundException;
    List<ImagesDTO> findAll();
}
