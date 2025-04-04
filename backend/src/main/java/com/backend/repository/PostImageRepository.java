package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.PostImageEntity;

public interface PostImageRepository extends JpaRepository<PostImageEntity, Long> {
}
