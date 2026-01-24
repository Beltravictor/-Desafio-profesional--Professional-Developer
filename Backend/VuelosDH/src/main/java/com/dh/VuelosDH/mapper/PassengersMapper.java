package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.PassengersAdminDTO;
import com.dh.VuelosDH.dto.PassengersDTO;
import com.dh.VuelosDH.entities.Passengers;
import com.dh.VuelosDH.entities.Tickets;
import org.springframework.stereotype.Component;

@Component
public class PassengersMapper {


    public Passengers toPass(PassengersDTO dto) {
        return Passengers.builder()
                .id(dto.getId())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .birthdate(dto.getBirthdate())
                .build();
    }

    public PassengersDTO toDto(Passengers passengers) {
        return PassengersDTO.builder()
                .id(passengers.getId())
                .firstname(passengers.getFirstname())
                .lastname(passengers.getLastname())
                .documentType(passengers.getDocumentType())
                .documentNumber(passengers.getDocumentNumber())
                .birthdate(passengers.getBirthdate())
                .tickets(passengers.getTickets()
                        .stream()
                        .map(Tickets::getId)
                        .toList())
                .build();
    }

    public Passengers AdminToPass(PassengersAdminDTO dto) {
        return Passengers.builder()
                .id(dto.getId())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .birthdate(dto.getBirthdate())
                .build();
    }

    public PassengersAdminDTO toAdmin(Passengers passengers, Long userId){
        return PassengersAdminDTO.builder()
                .id(passengers.getId())
                .firstname(passengers.getFirstname())
                .lastname(passengers.getLastname())
                .documentType(passengers.getDocumentType())
                .documentNumber(passengers.getDocumentNumber())
                .birthdate(passengers.getBirthdate())
                .tickets(passengers.getTickets()
                        .stream()
                        .map(Tickets::getId)
                        .toList())
                .user_id(userId)
                .build();
    }
}
