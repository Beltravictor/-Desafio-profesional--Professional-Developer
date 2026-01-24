package com.dh.VuelosDH.service.impl;

import com.dh.VuelosDH.dto.UserReviewsDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.UserReviews;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.DestinationsMapper;
import com.dh.VuelosDH.mapper.UserReviewsMapper;
import com.dh.VuelosDH.repository.IDestinationsRepository;
import com.dh.VuelosDH.repository.IUserReviewsRepository;
import com.dh.VuelosDH.service.IUserReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IUserReviewsServiceImpl implements IUserReviewsService {

    private final IUserReviewsRepository iUserReviewsRepository;
    private final IDestinationsRepository iDestinationsRepository;

    private final UserReviewsMapper userReviewsMapper;
    private final DestinationsMapper destinationsMapper;

    @Override
    public List<UserReviewsDTO> findByDestination(Long id) throws ResourceNotFoundException {
        List<UserReviewsDTO> userReviewsDTOS = new ArrayList<>();
        Destinations destination = iDestinationsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encontró el Destino con id: " + id)
                );

        List<UserReviews> reviews = iUserReviewsRepository.findAllById(destinationsMapper.toDto(destination).getReviews());

        for(UserReviews userReviews : reviews) {
            userReviewsDTOS.add(userReviewsMapper.toDto(userReviews));
        }

        return userReviewsDTOS;
    }

    @Override
    public List<UserReviewsDTO> findAll() throws ResourceNotFoundException {
        List<UserReviewsDTO> userReviewsDTOS = new ArrayList<>();
        List<UserReviews> reviews = iUserReviewsRepository.findAll();

        for(UserReviews userReviews : reviews) {
            userReviewsDTOS.add(userReviewsMapper.toDto(userReviews));
        }

        return userReviewsDTOS;
    }
}
