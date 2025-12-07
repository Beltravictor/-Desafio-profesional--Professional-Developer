package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Characteristics;
import com.dh.VuelosDH.entities.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICharacteristicsRepository extends JpaRepository<Characteristics, Long> {
    Optional<Characteristics> findByAddress(String address);
}
