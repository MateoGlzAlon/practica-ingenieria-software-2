package com.backend.repository;

import com.backend.persistence.entity.CommentVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.PostImageEntity;

public interface CommentVoteRepository extends JpaRepository<CommentVoteEntity, Long> {



}
