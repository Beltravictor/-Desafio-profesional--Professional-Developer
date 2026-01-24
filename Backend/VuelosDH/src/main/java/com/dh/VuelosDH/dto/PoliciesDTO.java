package com.dh.VuelosDH.dto;

import com.dh.VuelosDH.entities.Destinations;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoliciesDTO {

    private Long id;
    private String title;
    private String description;
}
