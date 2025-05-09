package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Override
    public CommentEntity findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<CommentInputDTO> findCommentsOfAPost(Long id) {
        List<CommentEntity> comments = commentRepository.findByPostId(id);

        return comments.stream()
                .map(comment -> CommentInputDTO.builder()
                        .id(comment.getId())
                        .post_id(comment.getPost().getId().intValue())
                        .content(comment.getContent())
                        .likes(comment.getLikes())
                        .build())
                .toList();
    }

    @Override
    public CommentEntity createComment(CommentInputDTO comment){

        CommentEntity newComment = new CommentEntity();

        PostEntity postComment = postRepository.findById((long)comment.getPost_id()).orElse(null);

        newComment.setPost(postComment);


        //change this when we have persistance
        UserEntity dummyUser = new UserEntity();
        dummyUser.setId(1L);
        newComment.setUser(dummyUser);



        newComment.setLikes(0);
        newComment.setContent(comment.getContent());
        newComment.setCreatedAt(new Date());


        return commentRepository.save(newComment);


    }

}

