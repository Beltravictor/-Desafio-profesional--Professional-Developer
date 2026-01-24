package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationsRepository extends JpaRepository<Reservations,Long> {
}
