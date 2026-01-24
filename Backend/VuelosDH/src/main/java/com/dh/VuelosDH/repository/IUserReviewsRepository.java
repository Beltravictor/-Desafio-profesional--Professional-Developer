package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.UserReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserReviewsRepository extends JpaRepository<UserReviews, Long> {
}
