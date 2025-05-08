package com.backend.repository;

import com.backend.persistence.entity.CommentEntity;

import com.backend.persistence.inputDTO.UserInputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    //review this code, idk if its good enough
    @Query("SELECT c FROM CommentEntity c WHERE c.post.id = :postId")
    List<CommentEntity> findByPostId(@Param("postId") Long postId);

    @Query("FROM CommentEntity c " +
            "WHERE c.user.id = :userId")
    List<CommentEntity> findByUserId(@Param("userId") Long postId);

    @Query("SELECT " +
      "FUNCTION('TO_CHAR', c.createdAt, 'Mon') AS month, " +
      "COUNT(c) AS contributions " +
      "FROM CommentEntity c " +
      "WHERE c.user.id = :userId " +
      "GROUP BY " +
      "MONTH(c.createdAt), " +
      "FUNCTION('TO_CHAR', c.createdAt, 'Mon') " +
      "ORDER BY MONTH(c.createdAt)")
    List<Object[]> findCommentCountsByMonth(@Param("userId") Long userId);
}
