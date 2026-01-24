package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketsRepository extends JpaRepository<Tickets, Long> {
}
