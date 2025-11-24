package com.dh.VuelosDH.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private Long id;
    @Column(name = "firstname")

    private String firstname;
    @Column(name = "lastname")

    private String lastname;
    @Column(name = "email")

    private String email;
    @Column(name = "password")

    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")

    private Role role;
    @OneToMany(mappedBy = "user")
    private Set<Flight> flight = new HashSet<>();

}
