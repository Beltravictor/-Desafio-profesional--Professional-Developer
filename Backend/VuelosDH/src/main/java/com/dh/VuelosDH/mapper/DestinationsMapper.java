package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.DestinationsDTO;
import com.dh.VuelosDH.dto.PoliciesDTO;
import com.dh.VuelosDH.entities.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DestinationsMapper {

    public DestinationsDTO toDto(Destinations des) {
        return DestinationsDTO.builder()
                .id(des.getId())
                .name(des.getName())
                .description(des.getDescription())
                .sample_price(des.getSample_price())
                .images(
                        des.getImages()
                                .stream()
                                .map(Images::getUrl)
                                .toList()
                )
                .categories(
                        des.getCategories()
                                .stream()
                                .map(d_c -> d_c.getCategory().getId())
                                .toList()
                )
                .characteristics(
                        des.getCharacteristics()
                                .stream()
                                .map(d_c -> d_c.getCharacteristics().getId())
                                .toList()
                )
                .policies(des.getPolicies()
                        .stream()
                        .map(p -> {
                            PoliciesDTO dto = new PoliciesDTO();
                            dto.setId(p.getId());
                            dto.setTitle(p.getTitle());
                            dto.setDescription(p.getDescription());
                            return dto;
                        })
                        .toList())
                .reviews(des.getReviews()
                        .stream()
                        .map(UserReviews::getId)
                        .toList())
                .build();
    }

    public Destinations toDes(DestinationsDTO dto, List<Category> categories, List<Characteristics> characteristics) {
        var des = Destinations.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .sample_price(dto.getSample_price())
                .build();

        dto.getImages().forEach(des::addImage);
        categories.forEach(des::addCategory);
        characteristics.forEach(des::addCharacteristic);

        return des;
    }
}
