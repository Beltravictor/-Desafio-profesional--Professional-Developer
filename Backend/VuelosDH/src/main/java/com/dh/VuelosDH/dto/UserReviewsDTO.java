package com.dh.VuelosDH.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReviewsDTO {

    private Long id;
    private Long destination_id;
    private String review;
    private Float stars;
}
