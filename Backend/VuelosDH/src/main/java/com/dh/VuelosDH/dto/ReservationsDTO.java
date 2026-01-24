package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Status;
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
    private Status reservationStatus;
    private Integer economyClass;
    private Integer premium_economyClass;
    private Integer businessClass;
    private Integer firstClass;
    private Float totalPrice;
    private Date creationDate;
    private Long startFlight;
    private Long returnFlight;
    private List<Long> tickets;
}
