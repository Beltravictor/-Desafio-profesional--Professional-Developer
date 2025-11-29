package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDestinationsRepository extends JpaRepository<Destinations, Long> {

    @Query(
            value = "SELECT * FROM destinations ORDER BY RANDOM() LIMIT :nro",
            nativeQuery = true)
    List<Destinations> randomsDestinations(@Param("nro") Long nro);

    @Query(
            value = """
                    SELECT DISTINCT D.DESTINATIONS_ID, D.NAME, D.SAMPLE_PRICE, D.DESCRIPTION, D.RATING
                    FROM DESTINATIONS D
                    JOIN DESTINATIONS_CATEGORY D_C
                    ON D.DESTINATIONS_ID = D_C .DESTINATIONS_ID
                    WHERE CATEGORY_ID = :id""",
            nativeQuery = true
    )
    List<Destinations> findByCategory(@Param("id") Long id);
    Optional<Destinations> findByName(String name);
}
