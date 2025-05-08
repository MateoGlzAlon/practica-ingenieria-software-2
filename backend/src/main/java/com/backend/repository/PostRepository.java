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

    @Query("SELECT new com.backend.persistence.outputdto.PostOutputDTO(" +
           "  p.id, " +
           "  p.title, " +
           "  p.likes, " +
           "  COUNT(c), " +
           "  p.createdAt" +
           ") " +
           "FROM PostEntity p " +
           "LEFT JOIN p.comments c " +
           "WHERE p.user.id = :userId " +
           "GROUP BY p.id, p.title, p.likes, p.createdAt " +
           "ORDER BY p.createdAt DESC")
    List<PostOutputDTO> findPostsByUserId(@Param("userId") Long userId);

}
