package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketsDTO {

    private Long id;
    private Integer seatNumber;
    private Status status;
    private Passengers_Classes passengersClasses;
    private Date creationDate;
    private Long reservation_id;
    private Long passenger_id;
    private Long flight_id;
}
