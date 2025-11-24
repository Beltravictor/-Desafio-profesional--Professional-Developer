package com.dh.VuelosDH.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DestinationsDTO {

    private Long id;
    private String name;
    private List<String> images = new ArrayList<>();
    private Set<Long> categories = new HashSet<>();
    private String description;
    private Double sample_price;
    private Float rating;
}