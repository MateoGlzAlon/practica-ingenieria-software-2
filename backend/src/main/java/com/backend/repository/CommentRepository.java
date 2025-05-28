package com.backend.repository;

import com.backend.persistence.entity.CommentEntity;

import com.backend.persistence.inputDTO.UserInputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    //review this code, idk if its good enough
    @Query("FROM CommentEntity c " +
            "WHERE c.post.id = :postId")
    List<CommentEntity> findByPostId(@Param("postId") Long postId, Sort sort);

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

        @Query("FROM CommentEntity c WHERE c.post.id = :postId ORDER BY c.votes DESC")
        List<CommentEntity> findByPostIdOrderByVotesDesc(@Param("postId") Long postId);

        @Query("FROM CommentEntity c WHERE c.post.id = :postId ORDER BY c.createdAt DESC")
        List<CommentEntity> findByPostIdOrderByCreatedAtDesc(@Param("postId") Long postId);

        @Query("FROM CommentEntity c WHERE c.post.id = :postId ORDER BY c.createdAt ASC")
        List<CommentEntity> findByPostIdOrderByCreatedAtAsc(@Param("postId") Long postId);

}
