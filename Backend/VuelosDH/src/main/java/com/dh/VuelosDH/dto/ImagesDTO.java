package com.dh.VuelosDH.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagesDTO {
    private Long id;

    @NotBlank(message = "La URL no puede estar vacía")
    private String url;

    @NotNull(message = "El ID del destino no puede ser nulo")
    @Positive(message = "El ID del destino debe ser un número positivo")
    private Long destination_id;
}