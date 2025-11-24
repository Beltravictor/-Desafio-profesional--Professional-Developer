package com.dh.VuelosDH.service;


import com.dh.VuelosDH.dto.ImagesDTO;
import com.dh.VuelosDH.entities.Images;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IImagesService {

    ImagesDTO save (ImagesDTO imagesDTO);
    Optional<ImagesDTO> findById(Long id) throws ResourceNotFoundException;
    void update(ImagesDTO imagesDTO);
    void delete(Long id) throws ResourceNotFoundException;
    List<ImagesDTO> findAll();
}
