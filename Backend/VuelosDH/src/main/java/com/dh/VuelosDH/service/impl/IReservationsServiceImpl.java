package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.entities.Reservations;
import com.dh.VuelosDH.entities.Tickets;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.ReservationsMapper;
import com.dh.VuelosDH.repository.IReservationsRepository;
import com.dh.VuelosDH.repository.ITicketsRepository;
import com.dh.VuelosDH.repository.IUserRepository;
import com.dh.VuelosDH.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IReservationsServiceImpl implements IReservationService {

    private final IReservationsRepository iReservationsRepository;
    private final IUserRepository iUserRepository;
    private final ITicketsRepository iTicketsRepository;

    private final ReservationsMapper reservationsMapper;

    @Override
    public ReservationsAdminDTO save(ReservationsAdminDTO dto) throws ResourceNotFoundException {
        var user = iUserRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        List<Tickets> tickets = iTicketsRepository.findAllById(dto.getTickets());
        if (tickets.size() != dto.getTickets().size()) {
            throw new ResourceNotFoundException("Algún ticket no existe");
        }

        Reservations res = reservationsMapper.AdminToRes(dto);
        res.setCreationDate(Calendar.getInstance().getTime());
        user.addReservation(res);
        iUserRepository.save(user);

        return reservationsMapper.toAdmin(res, user.getId());
    }

    @Override
    public ReservationsAdminDTO findById(Long id) throws ResourceNotFoundException {
        Reservations res = iReservationsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró la Reserva con id: " + id)
                );

        return reservationsMapper.toAdmin(res, res.getUser().getId());
    }

    @Override
    public ReservationsAdminDTO update(ReservationsAdminDTO dto) throws ResourceNotFoundException {
        Reservations reservation = iReservationsRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró la Reserva con id: " + dto.getId())
                );
        var user = iUserRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error usuario incorrecto"));

        List<Tickets> tickets = iTicketsRepository.findAllById(dto.getTickets());
        if (tickets.size() != dto.getTickets().size()) {
            throw new ResourceNotFoundException("Algún ticket no existe");
        }

        Reservations res = reservationsMapper.AdminToRes(dto);
        res.setCreationDate(reservation.getCreationDate());
        user.removeReservation(reservation);
        user.addReservation(res);
        iUserRepository.save(user);

        return reservationsMapper.toAdmin(res, user.getId());
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        iReservationsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se pudo eliminar la Reserva con id: " + id)
                );

        iReservationsRepository.deleteById(id);
    }

    @Override
    public List<ReservationsAdminDTO> findAll() {
        List<Reservations> reservations = iReservationsRepository.findAll();
        List<ReservationsAdminDTO> reservationsDTOS = new ArrayList<>();
        for (Reservations res : reservations) {
            reservationsDTOS.add(reservationsMapper.toAdmin(res, res.getUser().getId()));
        }
        return reservationsDTOS;
    }
}
