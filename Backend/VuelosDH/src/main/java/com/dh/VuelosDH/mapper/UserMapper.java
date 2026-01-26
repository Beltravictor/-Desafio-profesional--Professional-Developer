package com.dh.VuelosDH.mapper;

import com.dh.VuelosDH.dto.UserDTO;
import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.User;
import com.dh.VuelosDH.entities.UserReviews;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .creationDate(user.getCreationDate())
                .favorites(user.getFavorites()
                        .stream()
                        .map(Destinations::getId)
                        .collect(Collectors.toSet()))
                .reviews(user.getReviews().stream()
                        .map(UserReviews::getId)
                        .toList())
                .build();
    }

    public User toUser(UserDTO dto, String password) {
        return User.builder()
                .id(dto.getId())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .role(dto.getRole())
                .creationDate(dto.getCreationDate())
                .password(password)
                .build();
    }
}
