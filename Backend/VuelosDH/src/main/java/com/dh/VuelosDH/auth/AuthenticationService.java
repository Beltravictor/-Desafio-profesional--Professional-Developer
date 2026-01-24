package com.dh.VuelosDH.auth;

import com.dh.VuelosDH.configuration.JwtService;
import com.dh.VuelosDH.dto.UserDTO;
import com.dh.VuelosDH.entities.Role;
import com.dh.VuelosDH.entities.User;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.mapper.UserMapper;
import com.dh.VuelosDH.repository.IDestinationsRepository;
import com.dh.VuelosDH.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;
    private final IDestinationsRepository iDestinationsRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        if (iUserRepository.findByEmail(request.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .creationDate(Calendar.getInstance().getTime())
                .build();

        user.setReservations(new ArrayList<>());
        user.setPassengers(new ArrayList<>());

        iUserRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = iUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public List<UserDTO> findAll() {
        List<User> users = iUserRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(userMapper.toDto(user));
        }
        return usersDTO;
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<User> userToLookFor = iUserRepository.findById(id);
        if (userToLookFor.isPresent())
            iUserRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar el usuario con id: " + id);
    }

    public UserDTO update(UserDTO userDTO) {
        var password = iUserRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        User saved = iUserRepository.save(userMapper.toUser(userDTO, password.getPassword()));
       return userMapper.toDto(saved);
    }

}
