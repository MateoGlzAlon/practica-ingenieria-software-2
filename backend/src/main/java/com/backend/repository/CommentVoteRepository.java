package com.backend.repository;

import com.backend.persistence.entity.CommentVoteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentVoteRepository extends JpaRepository<CommentVoteEntity, Long> {


    @Query("SELECT CASE WHEN COUNT(cv) > 0 THEN true ELSE false END FROM CommentVoteEntity cv " +
            "WHERE cv.user.id = :userId AND cv.comment.id = :commentId")
    boolean isCommentVoted(@Param("userId") Long userId, @Param("commentId") Long commentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentVoteEntity cv " +
            "WHERE cv.user.id = :userId AND cv.comment.id = :commentId")
    void deleteByUserIDProjectId(@Param("userId") Long userId, @Param("commentId") Long commentId);

}
