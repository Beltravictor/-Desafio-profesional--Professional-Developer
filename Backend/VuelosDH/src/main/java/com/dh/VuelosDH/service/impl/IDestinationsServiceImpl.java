package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.Destinations_Category;
import com.dh.VuelosDH.entities.Destinations_Characteristics;
import com.dh.VuelosDH.entities.Images;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.ICategoryRepository;
import com.dh.VuelosDH.repository.ICharacteristicsRepository;
import com.dh.VuelosDH.repository.IDestinationsRepository;
import com.dh.VuelosDH.service.IDestinationsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IDestinationsServiceImpl implements IDestinationsService {

    private final IDestinationsRepository destinationsRepository;
    private final ICategoryRepository iCategoryRepository;
    private final ICharacteristicsRepository iCharacteristicsRepository;

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
        for (Long id : destinationsDTO.getCharacteristics()) {
            Destinations_Characteristics d_cEntity = new Destinations_Characteristics();

            if (iCharacteristicsRepository.findById(id).isPresent()) {
                d_cEntity.setCharacteristics(iCharacteristicsRepository.findById(id).get());
                d_cEntity.setDestination(destinationsEntity);
                destinationsEntity.getCharacteristics().add(d_cEntity);
            }
        }
        destinationsRepository.save(destinationsEntity);

        destinationsDTOToReturn.setId(destinationsEntity.getId());
        destinationsDTOToReturn.setName(destinationsDTO.getName());
        destinationsDTOToReturn.setDescription(destinationsDTO.getDescription());
        destinationsDTOToReturn.setSample_price(destinationsDTO.getSample_price());
        destinationsDTOToReturn.setImages(destinationsDTO.getImages());
        destinationsDTOToReturn.setCategories(destinationsDTO.getCategories());
        destinationsDTOToReturn.setCharacteristics(destinationsDTO.getCharacteristics());

        return destinationsDTOToReturn;
    }

    @Override
    public ResponseEntity<DestinationsDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Destinations> destinations = destinationsRepository.findById(id);
        if (destinations.isPresent()) {
            Destinations des = destinations.get();
            DestinationsDTO aux = DestinationsDTO.builder()
                    .id(des.getId())
                    .name(des.getName())
                    .description(des.getDescription())
                    .sample_price(des.getSample_price())
                    .rating(des.getRating())
                    .images(
                            des.getImages()
                                    .stream()
                                    .map(Images::getUrl)
                                    .toList()
                    )
                    .categories(
                            des.getCategories()
                                    .stream()
                                    .map(d_c -> d_c.getCategory().getId())
                                    .toList()
                    )
                    .characteristics(
                            des.getCharacteristics()
                                    .stream()
                                    .map(d_c -> d_c.getCharacteristics().getId())
                                    .toList()
                    )
                    .build();
            return ResponseEntity.ok(aux);
        } else {
            throw new ResourceNotFoundException("No se encontró el Destino con id: " + id);
        }

    }

    @Override
    public ResponseEntity<String> update(DestinationsDTO destinationsDTO) {

        Optional<Destinations> destinations = destinationsRepository.findById(destinationsDTO.getId());
        if (destinations.isPresent()) {
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
            for (Long id  : destinationsDTO.getCharacteristics()) {
                Destinations_Characteristics d_cEntity = new Destinations_Characteristics();

                if (iCharacteristicsRepository.findById(id).isPresent()) {
                    d_cEntity.setCharacteristics(iCharacteristicsRepository.findById(id).get());
                    d_cEntity.setDestination(destinationsEntity);
                    destinationsEntity.getCharacteristics().add(d_cEntity);
                }
            }
            destinationsRepository.save(destinationsEntity);
            return ResponseEntity.ok("Se actualizo el Destino con éxito");
        } else {
            return ResponseEntity.ok("No se pudo actualizar el Destino");
        }
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
            destinationsDTO.add(DestinationsDTO.builder()
                    .id(des.getId())
                    .name(des.getName())
                    .description(des.getDescription())
                    .sample_price(des.getSample_price())
                    .rating(des.getRating())
                    .images(
                            des.getImages()
                                    .stream()
                                    .map(Images::getUrl)
                                    .toList()
                    )
                    .categories(
                            des.getCategories()
                                    .stream()
                                    .map(d_c -> d_c.getCategory().getId())
                                    .toList()
                    )
                    .characteristics(
                            des.getCharacteristics()
                                    .stream()
                                    .map(d_c -> d_c.getCharacteristics().getId())
                                    .toList()
                    )
                    .build());
        }
        return destinationsDTO;
    }

    @Override
    public List<DestinationsDTO> randomsDestinations(Long nro) {
        List<Destinations> destinations = destinationsRepository.randomsDestinations(nro);
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations) {
            destinationsDTO.add(DestinationsDTO.builder()
                    .id(des.getId())
                    .name(des.getName())
                    .description(des.getDescription())
                    .sample_price(des.getSample_price())
                    .rating(des.getRating())
                    .images(
                            des.getImages()
                                    .stream()
                                    .map(Images::getUrl)
                                    .toList()
                    )
                    .categories(
                            des.getCategories()
                                    .stream()
                                    .map(d_c -> d_c.getCategory().getId())
                                    .toList()
                    )
                    .characteristics(
                            des.getCharacteristics()
                                    .stream()
                                    .map(d_c -> d_c.getCharacteristics().getId())
                                    .toList()
                    )
                    .build());
        }
        return destinationsDTO;
    }

    @Override
    public List<DestinationsDTO> findByCategory(Long id) {
        List<Destinations> destinations = destinationsRepository.findByCategory(id);
        List<DestinationsDTO> destinationsDTO = new ArrayList<>();
        for (Destinations des : destinations) {
            destinationsDTO.add(DestinationsDTO.builder()
                    .id(des.getId())
                    .name(des.getName())
                    .description(des.getDescription())
                    .sample_price(des.getSample_price())
                    .rating(des.getRating())
                    .images(
                            des.getImages()
                                    .stream()
                                    .map(Images::getUrl)
                                    .toList()
                    )
                    .categories(
                            des.getCategories()
                                    .stream()
                                    .map(d_c -> d_c.getCategory().getId())
                                    .toList()
                    )
                    .characteristics(
                            des.getCharacteristics()
                                    .stream()
                                    .map(d_c -> d_c.getCharacteristics().getId())
                                    .toList()
                    )
                    .build());
        }
        return destinationsDTO;
    }

    @Override
    public ResponseEntity<DestinationsDTO> findByName(String name) throws ResourceNotFoundException {
        Optional<Destinations> destinations = destinationsRepository.findByName(name);
        if (destinations.isPresent()) {
            Destinations des = destinations.get();
            DestinationsDTO aux = DestinationsDTO.builder()
                    .id(des.getId())
                    .name(des.getName())
                    .description(des.getDescription())
                    .sample_price(des.getSample_price())
                    .rating(des.getRating())
                    .images(
                            des.getImages()
                                    .stream()
                                    .map(Images::getUrl)
                                    .toList()
                    )
                    .categories(
                            des.getCategories()
                                    .stream()
                                    .map(d_c -> d_c.getCategory().getId())
                                    .toList()
                    )
                    .characteristics(
                            des.getCharacteristics()
                                    .stream()
                                    .map(d_c -> d_c.getCharacteristics().getId())
                                    .toList()
                    )
                    .build();
            return ResponseEntity.ok(aux);
        } else {
            throw new ResourceNotFoundException("No se encontró el Destino con nombre: " + name);
        }
    }
}
