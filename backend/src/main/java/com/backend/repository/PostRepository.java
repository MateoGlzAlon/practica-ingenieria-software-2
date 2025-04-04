package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
