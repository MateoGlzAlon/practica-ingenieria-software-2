package com.backend.service;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import java.util.List;

public interface CommentService {

    CommentEntity findCommentById(Long id);

    List<CommentOutputDTO> findCommentsOfAPost(Long id);

    CommentEntity createComment(CommentInputDTO comment);
}
