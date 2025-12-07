package com.dh.VuelosDH.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<@NotBlank(message = "La URL de la imagen no puede estar vacía") String> images = new ArrayList<>();

    @NotEmpty(message = "Debe tener al menos una categoría")
    private List<@NotNull(message = "El ID de categoría no puede ser nulo") Long> categories = new ArrayList<>();

    @NotEmpty(message = "Debe tener al menos una característica")
    private List<@NotNull(message = "El ID de característica no puede ser nulo") Long> characteristics = new ArrayList<>();

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double sample_price;

    @NotNull(message = "El rating no puede ser nulo")
    @Min(value = 0, message = "El rating no puede ser menor que 0")
    @Max(value = 5, message = "El rating no puede ser mayor que 5")
    private Float rating;
}