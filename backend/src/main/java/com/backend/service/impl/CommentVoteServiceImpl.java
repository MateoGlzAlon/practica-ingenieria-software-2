package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.inputDTO.CommentVoteInputDTO;
import com.backend.repository.CommentVoteRepository;
import com.backend.repository.UserRepository;
import com.backend.repository.CommentRepository;
import com.backend.service.CommentVoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentVoteServiceImpl implements CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentVoteEntity findCommentVoteById(Long id) {
        return commentVoteRepository.findById(id).orElse(null);
    }

    @Override
    public CommentVoteEntity createCommentVote(CommentVoteInputDTO commentVote) {
        Long userId = commentVote.getUserId();
        Long commentId = commentVote.getCommentId();

        boolean hasVoted = commentVoteRepository.isCommentVoted(userId, commentId);

        UserEntity user = userRepository.findById(userId).orElse(null);
        CommentEntity comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return null;
        }

        if (hasVoted) {
            commentVoteRepository.deleteByUserIDProjectId(userId, commentId);
            comment.decreaseVotes();
            commentRepository.save(comment);
            return null;
        } else {
            comment.increaseVotes();
            commentRepository.save(comment);

            CommentVoteEntity commentVoteEntity = CommentVoteEntity.builder()
                    .user(user)
                    .comment(comment)
                    .build();

            return commentVoteRepository.save(commentVoteEntity);
        }
    }

    @Override
    public boolean isCommentVoted(Long userId,Long commentId){
        if(userId == null) return false;

        return commentVoteRepository.isCommentVoted(userId, commentId);
    }
}
