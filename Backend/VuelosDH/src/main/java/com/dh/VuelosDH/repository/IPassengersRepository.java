package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPassengersRepository extends JpaRepository<Passengers, Long> {
}
