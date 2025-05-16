package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.TagEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("FROM TagEntity t " +
            "WHERE t.name = :tagName")
    Optional<TagEntity> findTagByName(@Param("tagName") String name);

    @Query(
      "SELECT t " +
      "FROM TagEntity t " +
      "ORDER BY function('RANDOM')"
    )
    Page<TagEntity> findAllRandom(Pageable pageable);
}

