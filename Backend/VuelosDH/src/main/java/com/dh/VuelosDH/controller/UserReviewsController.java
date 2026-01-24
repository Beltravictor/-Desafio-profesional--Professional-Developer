package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.dto.UserReviewsDTO;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.service.IUserReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class UserReviewsController {

    private final IUserReviewsService iUserReviewsService;

    @GetMapping("/destino/{id}")
    ResponseEntity<List<UserReviewsDTO>> findByDestination(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(iUserReviewsService.findByDestination(id));
    }

    @GetMapping
    ResponseEntity<List<UserReviewsDTO>> findAll() throws ResourceNotFoundException {
        return ResponseEntity.ok(iUserReviewsService.findAll());
    }
}
