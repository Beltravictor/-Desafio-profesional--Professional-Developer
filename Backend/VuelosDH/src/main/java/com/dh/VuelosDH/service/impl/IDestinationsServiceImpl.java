package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.Destinations_Category;
import com.dh.VuelosDH.entities.Images;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.ICategoryRepository;
import com.dh.VuelosDH.repository.IDestinationsRepository;
import com.dh.VuelosDH.service.IDestinationsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IDestinationsServiceImpl implements IDestinationsService {

    private final IDestinationsRepository destinationsRepository;
    private final ICategoryRepository iCategoryRepository;

    @Override
    public DestinationsDTO save(DestinationsDTO destinationsDTO) {
        Destinations destinationsEntity = new Destinations();
        DestinationsDTO destinationsDTOToReturn = new DestinationsDTO();

        destinationsEntity.setName(destinationsDTO.getName());
        destinationsEntity.setDescription(destinationsDTO.getDescription());
        destinationsEntity.setSample_price(destinationsDTO.getSample_price());
        destinationsEntity.setRating(destinationsDTO.getRating());
        for (String url : destinationsDTO.getImages()) {
            Images imagesEntity = new Images();
            imagesEntity.setUrl(url);
            imagesEntity.setDestination(destinationsEntity);
            destinationsEntity.getImages().add(imagesEntity);
        }
        for (Long id : destinationsDTO.getCategories()) {
            Destinations_Category d_cEntity = new Destinations_Category();

            if (iCategoryRepository.findById(id).isPresent()) {
                d_cEntity.setCategory(iCategoryRepository.findById(id).get());
                d_cEntity.setDestination(destinationsEntity);
                destinationsEntity.getCategories().add(d_cEntity);
                destinationsDTOToReturn.getCategories().add(id);
            }
        }
        destinationsRepository.save(destinationsEntity);

        destinationsDTOToReturn.setId(destinationsEntity.getId());
        destinationsDTOToReturn.setName(destinationsDTO.getName());
        destinationsDTOToReturn.setDescription(destinationsDTO.getDescription());
        destinationsDTOToReturn.setSample_price(destinationsDTO.getSample_price());
        destinationsDTOToReturn.setImages(destinationsDTO.getImages());

        return destinationsDTOToReturn;
    }

    @Override
    public Optional<DestinationsDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Destinations> destinations = destinationsRepository.findById(id);
        if (destinations.isPresent()) {
            Destinations des = destinations.get();
            DestinationsDTO aux = new DestinationsDTO();
            aux.setId(des.getId());
            aux.setName(des.getName());
            aux.setDescription(des.getDescription());
            aux.setSample_price(des.getSample_price());
            aux.setRating(des.getRating());
            for (Images img : des.getImages()) {
                aux.getImages().add(img.getUrl());
            }
            for (Destinations_Category d_c : des.getCategories()) {
                aux.getCategories().add(d_c.getCategory().getId());
            }
            return Optional.of(aux);
        } else {
            throw new ResourceNotFoundException("No se encontró el Destino con id: " + id);
        }

    }

    @Override
    public void update(DestinationsDTO destinationsDTO) {
        Destinations destinationsEntity = new Destinations();
        destinationsEntity.setId(destinationsDTO.getId());
        destinationsEntity.setName(destinationsDTO.getName());
        destinationsEntity.setDescription(destinationsDTO.getDescription());
        destinationsEntity.setSample_price(destinationsDTO.getSample_price());
        destinationsEntity.setRating(destinationsDTO.getRating());

        for (String url : destinationsDTO.getImages()) {
            Images imagesEntity = new Images();
            imagesEntity.setUrl(url);
            imagesEntity.setDestination(destinationsEntity);
            destinationsEntity.getImages().add(imagesEntity);
        }
        for (Long id : destinationsDTO.getCategories()) {
            Destinations_Category d_cEntity = new Destinations_Category();

            if (iCategoryRepository.findById(id).isPresent()) {
                d_cEntity.setCategory(iCategoryRepository.findById(id).get());
                d_cEntity.setDestination(destinationsEntity);
                destinationsEntity.getCategories().add(d_cEntity);
            }
        }
        destinationsRepository.save(destinationsEntity);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<Destinations> destinationsToLookFor = destinationsRepository.findById(id);
        if (destinationsToLookFor.isPresent())
            destinationsRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar el destino con id: " + id);
    }

    @Override
    public List<DestinationsDTO> findAll() {
        List<Destinations> destinations = destinationsRepository.findAll();
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations) {
            DestinationsDTO aux = new DestinationsDTO();
            aux.setId(des.getId());
            aux.setName(des.getName());
            aux.setDescription(des.getDescription());
            aux.setSample_price(des.getSample_price());
            aux.setRating(des.getRating());
            for (Images img : des.getImages()) {
                aux.getImages().add(img.getUrl());
            }
            for (Destinations_Category d_c : des.getCategories()) {
                aux.getCategories().add(d_c.getCategory().getId());
            }
            destinationsDTO.add(aux);
        }
        return destinationsDTO;
    }

    @Override
    public List<DestinationsDTO> randomsDestinations(Long nro) {
        List<Destinations> destinations = destinationsRepository.randomsDestinations(nro);
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations) {
            DestinationsDTO aux = new DestinationsDTO();
            aux.setId(des.getId());
            aux.setName(des.getName());
            aux.setDescription(des.getDescription());
            aux.setSample_price(des.getSample_price());
            aux.setRating(des.getRating());
            for (Images img : des.getImages()) {
                aux.getImages().add(img.getUrl());
            }
            for (Destinations_Category d_c : des.getCategories()) {
                aux.getCategories().add(d_c.getCategory().getId());
            }
            destinationsDTO.add(aux);
        }
        return destinationsDTO;
    }

    @Override
    public List<DestinationsDTO> findByCategory(Long id) {
        List<Destinations> destinations = destinationsRepository.findByCategory(id);
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations) {
            DestinationsDTO aux = new DestinationsDTO();
            aux.setId(des.getId());
            aux.setName(des.getName());
            aux.setDescription(des.getDescription());
            aux.setSample_price(des.getSample_price());
            aux.setRating(des.getRating());
            for (Images img : des.getImages()) {
                aux.getImages().add(img.getUrl());
            }
            for (Destinations_Category d_c : des.getCategories()) {
                aux.getCategories().add(d_c.getCategory().getId());
            }
            destinationsDTO.add(aux);
        }
        return destinationsDTO;
    }

    @Override
    public Optional<DestinationsDTO> findByName(String name) throws ResourceNotFoundException{
        Optional<Destinations> destinations = destinationsRepository.findByName(name);
        if (destinations.isPresent()) {
            Destinations des = destinations.get();
            DestinationsDTO aux = new DestinationsDTO();
            aux.setId(des.getId());
            aux.setName(des.getName());
            aux.setDescription(des.getDescription());
            aux.setSample_price(des.getSample_price());
            aux.setRating(des.getRating());
            for (Images img : des.getImages()) {
                aux.getImages().add(img.getUrl());
            }
            for (Destinations_Category d_c : des.getCategories()) {
                aux.getCategories().add(d_c.getCategory().getId());
            }
            return Optional.of(aux);
        } else {
            throw new ResourceNotFoundException("No se encontró el Destino con nombre: " + name);
        }
    }
}
