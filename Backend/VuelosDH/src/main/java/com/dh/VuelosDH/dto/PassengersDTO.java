package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Document_Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    private String firstname;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 40, message = "El apellido debe tener entre 2 y 40 caracteres")
    private String lastname;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Document_Type documentType;

    @NotBlank(message = "El número de documento es obligatorio")
    private String documentNumber;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private Date birthdate;
    private List<Long> tickets;
}
