package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationsDTO {

    private Long id;

    @NotNull(message = "El estado de la reserva es obligatorio")
    private Status reservationStatus;

    @Min(value = 0, message = "La cantidad de asientos en clase económica no puede ser negativa")
    private Integer economyClass;

    @Min(value = 0, message = "La cantidad de asientos en premium economy no puede ser negativa")
    private Integer premium_economyClass;

    @Min(value = 0, message = "La cantidad de asientos en clase business no puede ser negativa")
    private Integer businessClass;

    @Min(value = 0, message = "La cantidad de asientos en primera clase no puede ser negativa")
    private Integer firstClass;

    @NotNull(message = "El precio total es obligatorio")
    @Positive(message = "El precio total debe ser mayor a 0")
    private Float totalPrice;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private Date creationDate;

    @NotNull(message = "El vuelo de ida es obligatorio")
    @Positive(message = "El id del vuelo de ida debe ser positivo")
    private Long startFlight;
    private Long returnFlight;
    private List<Long> tickets;
}
