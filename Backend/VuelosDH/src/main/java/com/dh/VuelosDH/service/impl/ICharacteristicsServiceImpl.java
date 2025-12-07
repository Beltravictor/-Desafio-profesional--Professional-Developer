package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.CharacteristicsDTO;
import com.dh.VuelosDH.entities.Characteristics;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.ICharacteristicsRepository;
import com.dh.VuelosDH.service.ICharacteristicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ICharacteristicsServiceImpl implements ICharacteristicsService {

    private final ICharacteristicsRepository iCharacteristicsRepository;

    @Override
    public CharacteristicsDTO save(CharacteristicsDTO characteristicsDTO) {
        Characteristics characteristics = new Characteristics();
        CharacteristicsDTO dto = new CharacteristicsDTO();

        characteristics.setName(characteristicsDTO.getName());
        characteristics.setAddress(characteristicsDTO.getAddress());
        iCharacteristicsRepository.save(characteristics);

        dto.setName(characteristicsDTO.getName());
        dto.setId(characteristics.getId());
        dto.setAddress(characteristicsDTO.getAddress());
        return dto;
    }

    @Override
    public ResponseEntity<CharacteristicsDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Characteristics> characteristics = iCharacteristicsRepository.findById(id);
        if (characteristics.isPresent()) {
            Characteristics cha = characteristics.get();
            CharacteristicsDTO aux = new CharacteristicsDTO();
            aux.setId(cha.getId());
            aux.setName(cha.getName());
            aux.setAddress(cha.getAddress());
            return ResponseEntity.ok(aux);
        } else
            throw new ResourceNotFoundException("No se puedo eliminar la Característica con id: " + id);
    }

    @Override
    public ResponseEntity<String> update(CharacteristicsDTO characteristicsDTO) {
        Optional<Characteristics> characteristicToLookFor = iCharacteristicsRepository.findById(characteristicsDTO.getId());
        if (characteristicToLookFor.isPresent()) {
            Characteristics characteristics = new Characteristics();

            characteristics.setId(characteristicsDTO.getId());
            characteristics.setName(characteristicsDTO.getName());
            characteristics.setAddress(characteristicsDTO.getAddress());
            iCharacteristicsRepository.save(characteristics);
            return ResponseEntity.ok("Se actualizo la característica con éxito");
        } else
            return ResponseEntity.ok("No se pudo actualizar la característica");
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<Characteristics> characteristicToLookFor = iCharacteristicsRepository.findById(id);
        if (characteristicToLookFor.isPresent())
            iCharacteristicsRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar la característica con id: " + id);
    }

    @Override
    public List<CharacteristicsDTO> findAll() {
        List<Characteristics> characteristics = iCharacteristicsRepository.findAll();
        List<CharacteristicsDTO> dto = new ArrayList<>();
        for (Characteristics cha : characteristics) {
            CharacteristicsDTO aux = new CharacteristicsDTO();
            aux.setId(cha.getId());
            aux.setName(cha.getName());
            aux.setAddress(cha.getAddress());
            dto.add(aux);
        }
        return dto;
    }
}
