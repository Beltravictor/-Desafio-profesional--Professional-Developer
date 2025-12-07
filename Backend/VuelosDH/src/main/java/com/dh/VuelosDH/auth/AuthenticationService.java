package com.dh.VuelosDH.auth;

import com.dh.VuelosDH.configuration.JwtService;
import com.dh.VuelosDH.dto.UserDTO;
import com.dh.VuelosDH.entities.Role;
import com.dh.VuelosDH.entities.User;
import com.dh.VuelosDH.exception.ResourceNotFoundException;
import com.dh.VuelosDH.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
            var aux = UserDTO.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .creationDate(user.getCreationDate())
                    .build();
            usersDTO.add(aux);
        }
        return usersDTO;
    }

    public UserDTO getProfile(String email) {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .creationDate(user.getCreationDate())
                .build();
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<User> userToLookFor = iUserRepository.findById(id);
        if (userToLookFor.isPresent())
            iUserRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("No se puedo eliminar el usuario con id: " + id);
    }

    public ResponseEntity<String> update(UserDTO userDTO) {
        Optional<User> userToLookFor = iUserRepository.findById(userDTO.getId());
        if (userToLookFor.isPresent()) {
            var user = User.builder()
                    .id(userToLookFor.get().getId())
                    .firstname(userDTO.getFirstname())
                    .lastname(userDTO.getLastname())
                    .email(userDTO.getEmail())
                    .role(userDTO.getRole())
                    .creationDate(userToLookFor.get().getCreationDate())
                    .flight(userToLookFor.get().getFlight())
                    .password(userToLookFor.get().getPassword())
                    .build();
            iUserRepository.save(user);
            return ResponseEntity.ok("Se actualizo el Usuario con éxito");
        } else
            return ResponseEntity.ok("No se pudo actualizar el Usuario");
    }

    public void deleteMyUser(String email) {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        iUserRepository.deleteById(user.getId());
    }
}
