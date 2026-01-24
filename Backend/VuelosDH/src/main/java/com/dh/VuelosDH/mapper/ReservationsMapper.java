package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.dto.ReservationsDTO;
import com.dh.VuelosDH.entities.Reservations;
import com.dh.VuelosDH.entities.Tickets;
import com.dh.VuelosDH.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationsMapper {

    public ReservationsDTO toDto(Reservations res) {

        return ReservationsDTO.builder()
                .id(res.getId())
                .reservationStatus(res.getReservationStatus())
                .economyClass(res.getEconomyClass())
                .premium_economyClass(res.getPremium_economyClass())
                .businessClass(res.getBusinessClass())
                .firstClass(res.getFirstClass())
                .totalPrice(res.getTotalPrice())
                .creationDate(res.getCreationDate())
                .startFlight(res.getStartFlight().getId())
                .returnFlight(res.getReturnFlight() != null
                        ? res.getReturnFlight().getId()
                        : null)
                .tickets(res.getTickets().stream()
                        .map(Tickets::getId)
                        .toList())
                .build();
    }

    public ReservationsAdminDTO toAdmin(Reservations res, Long userId) {

        return ReservationsAdminDTO.builder()
                .id(res.getId())
                .reservationStatus(res.getReservationStatus())
                .economyClass(res.getEconomyClass())
                .premium_economyClass(res.getPremium_economyClass())
                .businessClass(res.getBusinessClass())
                .firstClass(res.getFirstClass())
                .totalPrice(res.getTotalPrice())
                .creationDate(res.getCreationDate())
                .startFlight(res.getStartFlight().getId())
                .returnFlight(res.getReturnFlight() != null
                        ? res.getReturnFlight().getId()
                        : null)
                .tickets(res.getTickets().stream()
                        .map(Tickets::getId)
                        .toList())
                .user_id(userId)
                .build();
    }

    public Reservations toRes(ReservationsDTO dto) {
        return Reservations.builder()
                .id(dto.getId())
                .reservationStatus(dto.getReservationStatus())
                .economyClass(dto.getEconomyClass() != null ? dto.getEconomyClass() : 0)
                .premium_economyClass(dto.getPremium_economyClass() != null ? dto.getPremium_economyClass() : 0)
                .businessClass(dto.getBusinessClass() != null ? dto.getBusinessClass() : 0)
                .firstClass(dto.getFirstClass() != null ? dto.getFirstClass() : 0)
                .totalPrice(dto.getTotalPrice() != null ? dto.getTotalPrice() : 0)
                .build();
    }

    public Reservations AdminToRes(ReservationsAdminDTO dto) {
        return Reservations.builder()
                .id(dto.getId())
                .economyClass(dto.getEconomyClass() != null ? dto.getEconomyClass() : 0)
                .premium_economyClass(dto.getPremium_economyClass() != null ? dto.getPremium_economyClass() : 0)
                .businessClass(dto.getBusinessClass() != null ? dto.getBusinessClass() : 0)
                .firstClass(dto.getFirstClass() != null ? dto.getFirstClass() : 0)
                .totalPrice(dto.getTotalPrice() != null ? dto.getTotalPrice() : 0)
                .build();
    }
}
