package com.backend.service;

import com.backend.persistence.entity.CommentEntity;

public interface CommentService {

    CommentEntity findCommentById(Long id);

}
