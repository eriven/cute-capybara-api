package com.rizzler.capyapi.repository;

import com.rizzler.capyapi.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "SELECT DISTINCT i FROM Image i JOIN i.tags t WHERE LOWER(t) = LOWER(:tag)",
           countQuery = "SELECT COUNT(DISTINCT i) FROM Image i JOIN i.tags t WHERE LOWER(t) = LOWER(:tag)")
    Page<Image> findByTag(@Param("tag") String tag, Pageable pageable);
}
