package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.PassengersAdminDTO;
import com.dh.VuelosDH.entities.Passengers;
import com.dh.VuelosDH.entities.Tickets;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.PassengersMapper;
import com.dh.VuelosDH.repository.IPassengersRepository;
import com.dh.VuelosDH.repository.ITicketsRepository;
import com.dh.VuelosDH.repository.IUserRepository;
import com.dh.VuelosDH.service.IPassengersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IPassengersServiceImpl implements IPassengersService {

    final private IPassengersRepository iPassengersRepository;
    final private IUserRepository iUserRepository;
    final private ITicketsRepository iTicketsRepository;
    final private PassengersMapper passengersMapper;

    @Override
    public PassengersAdminDTO save(PassengersAdminDTO dto) throws ResourceNotFoundException {
        var user = iUserRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        List<Tickets> tickets = iTicketsRepository.findAllById(dto.getTickets());
        if (tickets.size() != dto.getTickets().size()) {
            throw new ResourceNotFoundException("Algún ticket no existe");
        }

        Passengers passenger = passengersMapper.AdminToPass(dto);
        passenger.setTickets(tickets);
        user.addPassenger(passenger);

        iUserRepository.save(user);
        return passengersMapper.toAdmin(passenger, user.getId());
    }

    @Override
    public PassengersAdminDTO findById(Long id) throws ResourceNotFoundException {
        Passengers pass = iPassengersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));
        return passengersMapper.toAdmin(pass, pass.getUser().getId());
    }

    @Override
    public PassengersAdminDTO update(PassengersAdminDTO dto) throws ResourceNotFoundException {
        var user = iUserRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        iPassengersRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        List<Tickets> tickets = iTicketsRepository.findAllById(dto.getTickets());
        if (tickets.size() != dto.getTickets().size()) {
            throw new ResourceNotFoundException("Algún ticket no existe");
        }

        Passengers passenger = passengersMapper.AdminToPass(dto);
        passenger.setTickets(tickets);
        user.addPassenger(passenger);
        return passengersMapper.toAdmin(passenger, user.getId());
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        iPassengersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error pasajero incorrecto"));

        iPassengersRepository.deleteById(id);
    }

    @Override
    public List<PassengersAdminDTO> findAll() {
        List<Passengers> passengersList = iPassengersRepository.findAll();
        List<PassengersAdminDTO> dtoList = new ArrayList<>();

        for (Passengers pass : passengersList) {
            dtoList.add(passengersMapper.toAdmin(pass, pass.getUser().getId()));
        }

        return dtoList;
    }
}
