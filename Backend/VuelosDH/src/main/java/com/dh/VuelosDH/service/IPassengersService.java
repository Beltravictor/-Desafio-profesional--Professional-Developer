package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.PassengersAdminDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;

public interface IPassengersService {

    PassengersAdminDTO save (PassengersAdminDTO dto) throws ResourceNotFoundException;
    PassengersAdminDTO findById(Long id) throws ResourceNotFoundException;
    PassengersAdminDTO update(PassengersAdminDTO dto) throws ResourceNotFoundException;
    void deleteById(Long id) throws ResourceNotFoundException;
    List<PassengersAdminDTO> findAll();
}
