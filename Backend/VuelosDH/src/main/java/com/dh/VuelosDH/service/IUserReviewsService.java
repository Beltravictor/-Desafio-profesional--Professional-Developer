package com.dh.VuelosDH.service;

import com.dh.VuelosDH.dto.UserReviewsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserReviewsService {
    public List<UserReviewsDTO> findByDestination(Long id) throws ResourceNotFoundException;

    List<UserReviewsDTO> findAll() throws ResourceNotFoundException;
}
