package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.ReservationsAdminDTO;
import com.dh.VuelosDH.dto.ReservationsDTO;
import com.dh.VuelosDH.entities.Reservations;
import com.dh.VuelosDH.entities.Tickets;
import com.dh.VuelosDH.entities.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
                .departure_date(res.getDeparture_date())
                .return_date(res.getReturnFlight() != null
                        ? res.getReturn_date()
                        : null)
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
                .departure_date(res.getDeparture_date())
                .return_date(res.getReturnFlight() != null
                        ? res.getReturn_date()
                        : null)
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
                .departure_date(dto.getDeparture_date())
                .return_date(dto.getReturn_date() != null ?
                        dto.getReturn_date() :
                        null)
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
                .departure_date(dto.getDeparture_date())
                .return_date(dto.getReturn_date() != null ?
                        dto.getReturn_date() :
                        null)
                .build();
    }

    public String toBody(Reservations reservation) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (reservation.getReturnFlight() != null) {
            return String.format("""
                            Hola,
                            
                            Tu reserva fue registrada correctamente. A continuación, te compartimos los detalles:
                            
                            ━━━━━━━━━━━━━━━━━━━━━━
                            📌 Detalle de la reserva
                            ━━━━━━━━━━━━━━━━━━━━━━
                            
                            • Número de reserva: %d
                            • Estado: CREADA
                            • Fecha de creación: %s
                            
                            ✈ Vuelos incluidos
                            
                            - Vuelo de ida
                            Nro: %s
                            Destino: %s
                            Fecha: %s
                            
                            - Vuelo de regreso
                            Nro: %s
                            Destino: %s
                            Fecha: %s
                            
                            🎟 Clases reservadas
                            - Económica: %d
                            - Premium Economy: %d
                            - Business: %d
                            - First Class: %d
                            
                            💰 Precio total: $%.2f
                            
                            ━━━━━━━━━━━━━━━━━━━━━━
                            
                            Te recomendamos conservar este email como comprobante de tu reserva.
                            Si necesitás modificar o cancelar tu viaje, podés hacerlo desde tu cuenta o contactando a nuestro equipo de soporte.
                            
                            Gracias por elegirnos y ¡buen viaje!
                            
                            Saludos,
                            VuelosDH
                            """,
                    reservation.getId(),
                    reservation.getCreationDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .format(formatter),

                    reservation.getStartFlight().getId(),
                    reservation.getStartFlight().getDeparture_date().toInstant().equals(reservation.getDeparture_date().toInstant()) ? reservation.getStartFlight().getOrigin().getName() : reservation.getStartFlight().getDestination().getName(),
                    reservation.getDeparture_date().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .format(formatter),

                    reservation.getReturnFlight().getId(),
                    reservation.getReturnFlight().getReturn_date().toInstant().equals(reservation.getReturn_date().toInstant()) ? reservation.getReturnFlight().getDestination().getName() : reservation.getReturnFlight().getOrigin().getName(),
                    reservation.getReturn_date().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .format(formatter),

                    reservation.getEconomyClass(),
                    reservation.getPremium_economyClass(),
                    reservation.getBusinessClass(),
                    reservation.getFirstClass(),
                    reservation.getTotalPrice()
            );
        } else
            return String.format("""
                            Hola,
                            
                            Tu reserva fue registrada correctamente. A continuación, te compartimos los detalles:
                            
                            ━━━━━━━━━━━━━━━━━━━━━━
                            📌 Detalle de la reserva
                            ━━━━━━━━━━━━━━━━━━━━━━
                            
                            • Número de reserva: %d
                            • Estado: CREADA
                            • Fecha de creación: %s
                            
                            ✈ Vuelos incluidos
                            
                            - Vuelo de ida
                            Nro: %s
                            Origen: %s
                            Destino: %s
                            Fecha: %s
                            
                            🎟 Clases reservadas
                            - Económica: %d
                            - Premium Economy: %d
                            - Business: %d
                            - First Class: %d
                            
                            💰 Precio total: $%.2f
                            
                            ━━━━━━━━━━━━━━━━━━━━━━
                            
                            Te recomendamos conservar este email como comprobante de tu reserva.
                            Si necesitás modificar o cancelar tu viaje, podés hacerlo desde tu cuenta o contactando a nuestro equipo de soporte.
                            
                            Gracias por elegirnos y ¡buen viaje!
                            
                            Saludos,
                            VuelosDH
                            """,
                    reservation.getId(),
                    reservation.getCreationDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .format(formatter),

                    reservation.getStartFlight().getId(),
                    reservation.getStartFlight().getDeparture_date().toInstant().equals(reservation.getDeparture_date().toInstant()) ? reservation.getStartFlight().getOrigin().getName() : reservation.getStartFlight().getDestination().getName(),
                    reservation.getStartFlight().getDeparture_date().toInstant().equals(reservation.getDeparture_date().toInstant()) ? reservation.getStartFlight().getDestination().getName() : reservation.getStartFlight().getOrigin().getName(),
                    reservation.getDeparture_date().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .format(formatter),

                    reservation.getEconomyClass(),
                    reservation.getPremium_economyClass(),
                    reservation.getBusinessClass(),
                    reservation.getFirstClass(),
                    reservation.getTotalPrice()
            );

    }

    public String toSubject(Reservations reservation) {
        return "Detalle de reserva " + reservation.getId() + " VuelosDH";
    }

}
