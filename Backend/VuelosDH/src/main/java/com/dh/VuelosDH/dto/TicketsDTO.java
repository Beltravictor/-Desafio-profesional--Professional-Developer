package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketsDTO {

    private Long id;

    @NotNull(message = "El número de asiento es obligatorio")
    @Min(value = 1, message = "El número de asiento debe ser mayor a 0")
    private Integer seatNumber;

    @NotNull(message = "El estado del ticket es obligatorio")
    private Status status;

    @NotNull(message = "La clase del pasajero es obligatoria")
    private Passengers_Classes passengersClasses;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private Date creationDate;

    @NotNull(message = "La reserva es obligatoria")
    @Positive(message = "El id de la reserva debe ser positivo")
    private Long reservation_id;

    @Positive(message = "El id del pasajero debe ser positivo")
    private Long passenger_id;

    @NotNull(message = "El vuelo es obligatorio")
    @Positive(message = "El id del vuelo debe ser positivo")
    private Long flight_id;
}
