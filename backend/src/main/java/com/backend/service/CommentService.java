package com.backend.service;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import java.util.List;

public interface CommentService {

    CommentEntity findCommentById(Long id);

    List<CommentInputDTO> findCommentsOfAPost(Long id);

    CommentEntity createComment(CommentInputDTO comment);
}
