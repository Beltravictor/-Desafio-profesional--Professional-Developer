package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Document_Type;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengersDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private Document_Type documentType;
    private String documentNumber;
    private Date birthdate;
    private List<Long> tickets;
}
