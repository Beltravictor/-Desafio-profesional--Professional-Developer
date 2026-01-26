package com.dh.VuelosDH.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReviewsDTO {

    private Long id;
    @NotNull(message = "El destination_id es obligatorio")
    private Long destination_id;

    @NotBlank(message = "La review no puede estar vacía")
    @Size(min = 0, max = 500, message = "La review debe tener entre 0 y 500 caracteres")
    private String review;

    @NotNull(message = "La puntuación es obligatoria")
    @DecimalMin(value = "0.5", inclusive = true, message = "La puntuación mínima es 0.5")
    @DecimalMax(value = "5.0", inclusive = true, message = "La puntuación máxima es 5")
    private Float stars;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private Date creationDate;
}
