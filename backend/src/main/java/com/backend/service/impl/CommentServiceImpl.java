package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;
import com.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public CommentEntity findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<CommentOutputDTO> findCommentsOfAPost(Long id) {
        List<CommentEntity> comments = commentRepository.findByPostId(id);

        return comments.stream()
                .map(comment -> CommentOutputDTO.builder()
                        .id(comment.getId())
                        .postId(comment.getPost().getId())
                        .userId(comment.getUser().getId())
                        .content(comment.getContent())
                        .votes(comment.getVotes())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public CommentEntity createComment(CommentInputDTO comment){

        /*
        EXAMPLE OF BODY OF REQUEST:
            {
              "postId": 1,
              "userId": 1,
              "content": "WOW!!!!!!!!!! NICE ONE LOL"
            }
        */

        CommentEntity newComment = CommentEntity.builder()
                //id no se pone, se asigna solo
                .post(postRepository.findById(comment.getPostId()).orElse(null))
                .user(userRepository.findById(comment.getUserId()).orElse(null))
                //los likes se inician a 0
                .likes(0)
                .content(comment.getContent())
                //setteo el date actual
                .createdAt(new Date())
                .build();

        return commentRepository.save(newComment);
     }

}

