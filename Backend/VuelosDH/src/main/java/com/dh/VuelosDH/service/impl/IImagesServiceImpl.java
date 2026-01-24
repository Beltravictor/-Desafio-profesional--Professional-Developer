package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.ImagesDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.Images;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.IDestinationsRepository;
import com.dh.VuelosDH.repository.IImagesRepository;
import com.dh.VuelosDH.service.IImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IImagesServiceImpl implements IImagesService {

    private final IImagesRepository iImagesrepository;
    private final IDestinationsRepository iDestinationsRepository;

    @Override
    public ImagesDTO save(ImagesDTO imagesDTO) {
        Images img = new Images();
        ImagesDTO imgDTOToReturn = new ImagesDTO();
        img.setUrl(imagesDTO.getUrl());
        Optional<Destinations> destinations = iDestinationsRepository.findById(imagesDTO.getDestination_id());
        if (destinations.isPresent()) {
            img.setDestination(destinations.get());
            imgDTOToReturn.setDestination_id(destinations.get().getId());
        }
        iImagesrepository.save(img);
        imgDTOToReturn.setUrl(imagesDTO.getUrl());
        imgDTOToReturn.setId(img.getId());
        return imgDTOToReturn;
    }

    @Override
    public ResponseEntity<ImagesDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Images> images = iImagesrepository.findById(id);
        if (images.isPresent()) {
            ImagesDTO imagesDTO = new ImagesDTO();
            Images img = images.get();
            imagesDTO.setId(img.getId());
            imagesDTO.setUrl(img.getUrl());
            if (img.getDestination() != null) {
                imagesDTO.setDestination_id(img.getDestination().getId());
            }
            return ResponseEntity.ok(imagesDTO);
        } else
            throw new ResourceNotFoundException("No se encontro Imagen con id: " + id);
    }

    @Override
    public void update(ImagesDTO imagesDTO) {
        Images img = new Images();
        img.setUrl(imagesDTO.getUrl());
        Optional<Destinations> destinations = iDestinationsRepository.findById(imagesDTO.getDestination_id());
        destinations.ifPresent(img::setDestination);
        iImagesrepository.save(img);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Images> destinationsToLookFor = iImagesrepository.findById(id);
        if (destinationsToLookFor.isPresent())
            iImagesrepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar la Imagen con id: " + id);
    }

    @Override
    public List<ImagesDTO> findAll() {
        List<Images> images = iImagesrepository.findAll();
        List<ImagesDTO> imagesDTOS = new ArrayList<>();
        for (Images img : images) {
            ImagesDTO imgDTOToAdd = new ImagesDTO();
            imgDTOToAdd.setId(img.getId());
            imgDTOToAdd.setUrl(img.getUrl());
            if (img.getDestination() != null) {
                imgDTOToAdd.setDestination_id(img.getDestination().getId());
            }
            imagesDTOS.add(imgDTOToAdd);
        }
        return imagesDTOS;
    }
}
