package com.dh.VuelosDH.dto;

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
    private String url;
    private Long destination_id;
}