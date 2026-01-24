package com.dh.VuelosDH.repository;

import com.dh.VuelosDH.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImagesRepository extends JpaRepository<Images,Long> {

}
