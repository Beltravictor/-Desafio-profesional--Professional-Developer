package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Policies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPoliciesRepository extends JpaRepository<Policies, Long> {
}
