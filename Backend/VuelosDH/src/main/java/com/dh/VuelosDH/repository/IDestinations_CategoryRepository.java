package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Destinations_Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDestinations_CategoryRepository extends JpaRepository<Destinations_Category,Long> {
}
