package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
