package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.dto.TicketsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ITicketsService {

    TicketsDTO save (TicketsDTO dto) throws ResourceNotFoundException;
    TicketsDTO findById(Long id) throws ResourceNotFoundException;
    TicketsDTO update(TicketsDTO dto) throws ResourceNotFoundException;
    void deleteById(Long id) throws ResourceNotFoundException;
    List<TicketsDTO> findAll();
    List<TicketsDTO> createTickets(ReservationsAdminDTO dto) throws ResponseStatusException;
}
