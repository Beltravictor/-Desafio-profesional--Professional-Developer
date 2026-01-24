package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    private String firstname;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 40, message = "El apellido debe tener entre 2 y 40 caracteres")
    private String lastname;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotNull(message = "El rol no puede ser nulo")
    private Role role;

    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private Date creationDate;

    private Set<Long> favorites;

}
