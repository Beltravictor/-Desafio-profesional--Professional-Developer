package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.UserReviewsDTO;
import com.dh.VuelosDH.entities.UserReviews;
import org.springframework.stereotype.Component;

@Component
public class UserReviewsMapper {

    public UserReviewsDTO toDto (UserReviews userReviews) {
        return UserReviewsDTO.builder()
                .id(userReviews.getId())
                .destination_id(userReviews.getDestination().getId())
                .review(userReviews.getReview())
                .stars(userReviews.getStars())
                .build();
    }
}
