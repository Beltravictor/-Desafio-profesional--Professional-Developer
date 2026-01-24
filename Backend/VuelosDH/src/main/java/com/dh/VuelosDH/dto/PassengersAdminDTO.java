package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Document_Type;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengersAdminDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private Document_Type documentType;
    private String documentNumber;
    private Date birthdate;
    private List<Long> tickets;
    private Long user_id;
}
