package com.dh.VuelosDH.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightsDTO {

    private Long id;

    @NotNull(message = "El origen es obligatorio")
    @Positive(message = "El id del origen debe ser positivo")
    private Long origin;

    @NotNull(message = "El destino es obligatorio")
    @Positive(message = "El id del destino debe ser positivo")
    private Long destination;

    @PastOrPresent(message = "La fecha de finalización no puede ser futura")
    private Date date_of_completion;

    @NotNull(message = "La fecha de salida es obligatoria")
    @FutureOrPresent(message = "La fecha de salida no puede ser pasada")
    private Date departure_date;

    @FutureOrPresent(message = "La fecha de regreso no puede ser pasada")
    private Date return_date;

    @NotNull(message = "La cantidad total de asientos es obligatoria")
    @Positive(message = "La cantidad total de asientos debe ser mayor a 0")
    private Integer totalSeats;

    @NotNull(message = "La cantidad de asientos disponibles es obligatoria")
    @Min(value = 0, message = "Los asientos disponibles no pueden ser negativos")
    private Integer availableSeats;

    private List<Long> startFlight;
    private List<Long> returnFlight;
    private List<Long> tickets;
}
