package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.TipEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TipRepository extends JpaRepository<TipEntity, Long> {


    @Query("FROM TipEntity t " +
            "WHERE t.post.id = :postId")
    public List<TipEntity> findTipsByPostId(@Param("postId") Long postId);

}