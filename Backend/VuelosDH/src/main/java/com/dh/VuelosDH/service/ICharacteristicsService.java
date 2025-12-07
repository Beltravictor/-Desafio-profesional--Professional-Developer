package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.CharacteristicsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICharacteristicsService {
    CharacteristicsDTO save (CharacteristicsDTO characteristicsDTO);
    ResponseEntity<CharacteristicsDTO> findById(Long id) throws ResourceNotFoundException;
    ResponseEntity<String> update(CharacteristicsDTO characteristicsDTO);
    void deleteById(Long id) throws ResourceNotFoundException;
    List<CharacteristicsDTO> findAll();
}
