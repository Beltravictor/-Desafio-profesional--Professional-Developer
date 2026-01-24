package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;

public interface IReservationService {

    ReservationsAdminDTO save (ReservationsAdminDTO reservationsDTO) throws ResourceNotFoundException;
    ReservationsAdminDTO findById(Long id) throws ResourceNotFoundException;
    ReservationsAdminDTO update(ReservationsAdminDTO reservationsDTO) throws ResourceNotFoundException;
    void deleteById(Long id) throws ResourceNotFoundException;
    List<ReservationsAdminDTO> findAll();
}
