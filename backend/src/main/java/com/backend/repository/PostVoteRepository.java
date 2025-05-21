package com.backend.repository;

import com.backend.persistence.entity.PostVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostVoteRepository extends JpaRepository<PostVoteEntity, Long> {


    @Query("SELECT CASE WHEN COUNT(pv) > 0 THEN true ELSE false END FROM PostVoteEntity pv " +
            "WHERE pv.user.id = :userId AND pv.post.id = :postId")
    boolean isPostVoted(@Param("userId") Long userId, @Param("postId") Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PostVoteEntity pv " +
            "WHERE pv.user.id = :userId AND pv.post.id = :postId")
    void deleteByUserIDProjectId(@Param("userId") Long userId, @Param("postId") Long postId);

}
