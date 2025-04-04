package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
