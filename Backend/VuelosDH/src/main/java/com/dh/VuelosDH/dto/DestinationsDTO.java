package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Flights;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationsDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotEmpty(message = "Debe incluir al menos una imagen")
    private List<@NotBlank(message = "La URL de la imagen no puede estar vacía") String> images;

    @NotEmpty(message = "Debe tener al menos una categoría")
    private List<@NotNull(message = "El ID de categoría no puede ser nulo") Long> categories;

    @NotEmpty(message = "Debe tener al menos una característica")
    private List<@NotNull(message = "El ID de característica no puede ser nulo") Long> characteristics;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double sample_price;

    private List<PoliciesDTO> policies;

    private List<Long> reviews;

}