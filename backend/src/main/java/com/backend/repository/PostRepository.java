package com.backend.repository;

import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("FROM PostEntity p " +
            "WHERE p.id = :postId")
    PostEntity getFeedPosts(@Param("postId") Long id);

    @Query("FROM PostEntity p " +
            "WHERE p.user.id = :userId")
    List<PostEntity> findPostsByUserId(@Param("userId") Long userId);

}
