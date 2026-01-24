package com.dh.VuelosDH.dto;

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
    private Long origin;
    private Long destination;
    private Date date_of_completion;
    private Date departure_date;
    private Date return_date;
    private Integer totalSeats;
    private Integer availableSeats;
    private List<Long> startFlight;
    private List<Long> returnFlight;
    private List<Long> tickets = new ArrayList<>();
}
