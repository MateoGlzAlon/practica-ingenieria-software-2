package com.backend.repository;

import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("FROM PostEntity p " +
            "WHERE p.id = :postId")
    PostEntity getFeedPosts(@Param("postId") Long id);

}
