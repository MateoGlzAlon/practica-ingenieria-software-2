package com.backend.repository;

import com.backend.persistence.entity.PostVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVoteEntity, Long> {



}
