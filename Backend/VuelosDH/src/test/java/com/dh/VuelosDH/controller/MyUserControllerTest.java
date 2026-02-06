package com.dh.VuelosDH.controller;

import com.dh.VuelosDH.auth.AuthenticationService;
import com.dh.VuelosDH.auth.RegisterRequest;
import com.dh.VuelosDH.dto.ReservationsDTO;
import com.dh.VuelosDH.entities.Status;
import com.dh.VuelosDH.service.IMyUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMyUserService iMyUserService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    ObjectMapper objectMapper;

    private String bearerToken;

    @BeforeAll
    void dataLoad() {
        RegisterRequest request = RegisterRequest.builder()
                .firstname("Test")
                .lastname("Test")
                .email("victorbeltramino1@gmail.com")
                .password("testTEST1!")
                .build();

        var response = authenticationService.register(request);
        bearerToken = response.getToken();
    }

    @Test
    @Order(1)
    void testGetProfile() throws Exception {
        mockMvc.perform(get("/myuser/profile")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("Test"))
                .andExpect(jsonPath("$.lastname").value("Test"))
                .andExpect(jsonPath("$.email").value("victorbeltramino1@gmail.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"))
                .andExpect(jsonPath("$.creationDate").exists())
                .andExpect(jsonPath("$.favorites").isArray())
                .andExpect(jsonPath("$.favorites").isEmpty())
                .andExpect(jsonPath("$.reviews").isArray())
                .andExpect(jsonPath("$.reviews").isEmpty());
    }

    @AfterAll
    void testDeleteMyUser() throws Exception {
        mockMvc.perform(delete("/myuser")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Se elimino su usuario con Ã©xito"));
    }

    @Test
    @Order(2)
    void testAddFavorite() throws Exception {
        mockMvc.perform(put("/myuser/addfavorite/1")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Se agregÃ³ el destino favorito con Ã©xito"));
    }


    @Test
    @Order(3)
    void testRemoveFavorite() throws Exception {
        mockMvc.perform(put("/myuser/removefavorite/1")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Se eliminÃ³ el destino favorito con Ã©xito"));
    }

    @Test
    void testSaveMyReservation() throws Exception {
        ReservationsDTO dto = ReservationsDTO.builder()
                .economyClass(1)
                .premium_economyClass(0)
                .businessClass(0)
                .firstClass(0)
                .startFlight(3645L)
                .totalPrice(1000.0F)
                .departure_date(new Date())
                .build();

        mockMvc.perform(post("/myuser/reserva")
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationStatus").value(Status.CREATED.name()))
                .andExpect(jsonPath("$.economyClass").value(1))
                .andExpect(jsonPath("$.premium_economyClass").value(0))
                .andExpect(jsonPath("$.businessClass").value(0))
                .andExpect(jsonPath("$.firstClass").value(0))
                .andExpect(jsonPath("$.totalPrice").value(1000))
                .andExpect(jsonPath("$.creationDate").exists())
                .andExpect(jsonPath("$.departure_date").exists())
                .andExpect(jsonPath("$.return_date").isEmpty())
                .andExpect(jsonPath("$.startFlight").value(3645))
                .andExpect(jsonPath("$.returnFlight").isEmpty())
                .andExpect(jsonPath("$.tickets").isEmpty());
    }
//
//    @Test
//    void testFindMyReservationById() {
//    }
//
//    @Test
//    void testFindMyReservations() {
//    }
//
//    @Test
//    void testUpdateMyReservation() {
//    }
//
//    @Test
//    void testDeleteMyReservationById() {
//    }
//
//    @Test
//    void testMyReservationsFlights() {
//    }
//
//    @Test
//    void testSaveMyPassenger() {
//    }
//
//    @Test
//    void testFindMyPassengerById() {
//    }
//
//    @Test
//    void testFindMyPassengers() {
//    }
//
//    @Test
//    void testUpdateMyPassenger() {
//    }
//
//    @Test
//    void testDeleteMyPassengerById() {
//    }
//
//    @Test
//    void testFindMyTicketById() {
//    }
//
//    @Test
//    void testFindMyTickets() {
//    }
//
//    @Test
//    void testUpdateMyTicket() {
//    }
//
//    @Test
//    void testDeleteMyTicketById() {
//    }
//
//    @Test
//    void testFindMyFavoritesDestinations() {
//    }
//
//    @Test
//    void testFindMyReviews() {
//    }
//
//    @Test
//    void testCreateMyReview() {
//    }
//
//    @Test
//    void testDeleteMyReview() {
//    }
}