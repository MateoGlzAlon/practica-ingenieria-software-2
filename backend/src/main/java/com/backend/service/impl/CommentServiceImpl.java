package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


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
                        .post_id(comment.getPost().getId().intValue()) // o haz cast seguro si es Long
                        .content(comment.getContent())
                        .likes(comment.getLikes())
                        .build())
                .toList();
    }

}

