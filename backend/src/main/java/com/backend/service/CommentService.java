package com.backend.service;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.persistence.inputDTO.CommentAcceptDTO;
import com.backend.persistence.outputdto.UserCommentDTO;

import java.util.List;

public interface CommentService {

    CommentEntity findCommentById(Long id);

    List<CommentOutputDTO> findCommentsOfAPost(Long id, String sort);

    CommentEntity createComment(CommentInputDTO comment);

    List<CommentEntity> getCommentsByPostIdOrderByVotes(Long postId);

    List<CommentEntity> getCommentsByPostIdOrderByNewest(Long postId);

    List<CommentEntity> getCommentsByPostIdOrderByOldest(Long postId);
  
    CommentEntity acceptComment(CommentAcceptDTO comment);

    List<UserCommentDTO> getCommentsOfAUser(Long idUser);

    void deleteCommentById(Long id);
}
