package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Destinations;
import com.dh.VuelosDH.entities.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFlightsRepository extends JpaRepository<Flights, Long> {

    @Query(
            value = """
                    SELECT *
                    FROM Flights
                    WHERE destination_origin_id = :ori_id
                    AND destination_destination_id = :des_id
                    """,
            nativeQuery = true
    )
    List<Flights> findAllByOriginDestination(@Param("ori_id") Long ori_id, @Param("des_id") Long des_id);
}
