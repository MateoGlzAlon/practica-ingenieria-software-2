package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.persistence.inputDTO.CommentAcceptDTO;
import com.backend.repository.CommentRepository;
import com.backend.persistence.outputdto.UserCommentDTO;
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
    public List<CommentOutputDTO> findCommentsOfAPost(Long id, String sort) {
        List<CommentEntity> comments;

        switch (sort.toLowerCase()) {
            case "votes":
                comments = commentRepository.findByPostIdOrderByVotesDesc(id);
                break;
            case "oldest":
                comments = commentRepository.findByPostIdOrderByCreatedAtAsc(id);
                break;
            case "newest":
            default:
                comments = commentRepository.findByPostIdOrderByCreatedAtDesc(id);
                break;
        }


        return comments.stream()
                .map(comment -> CommentOutputDTO.builder()
                        .id(comment.getId())
                        .authorProfilePicture(comment.getUser().getAvatarUrl())
                        .author(comment.getUser().getUsername())
                        .authorId(comment.getUser().getId())
                        .content(comment.getContent())
                        .votes(comment.getVotes())
                        .createdAt(comment.getCreatedAt())
                        .accepted(comment.isAccepted())
                        .build())
                .toList();
    }

    @Override
    public CommentEntity createComment(CommentInputDTO comment){

        CommentEntity newComment = CommentEntity.builder()
                //id no se pone, se asigna solo
                .post(postRepository.findById(comment.getPostId()).orElse(null))
                .user(userRepository.findById(comment.getUserId()).orElse(null))
                //los votes se inician a 0
                .votes(0)
                .content(comment.getContent())
                //setteo el date actual
                .createdAt(new Date())
                .build();

        return commentRepository.save(newComment);
    }

    public CommentEntity acceptComment(CommentAcceptDTO comment) {
        List<PostEntity> posts = postRepository.findPostsByUserId(comment.getUserId());

        boolean isValidUserVerification = posts.stream()
                .anyMatch(post -> Objects.equals(post.getId(), comment.getPostId()));

        if (!isValidUserVerification) {
            return null;
        }

        Optional<CommentEntity> optionalComment = commentRepository.findById(comment.getCommentId());
        if (optionalComment.isEmpty()) {
            return null;
        }

        CommentEntity updateComment = optionalComment.get();
        updateComment.setAccepted(!updateComment.isAccepted());

        return commentRepository.save(updateComment);
    }



    public List<UserCommentDTO> getCommentsOfAUser(Long idUser){

        List<CommentEntity> searchComments = commentRepository.findByUserId(idUser);

        List<UserCommentDTO> listAllCommentsUser = new ArrayList<>();

        searchComments.sort(Comparator.comparingInt(CommentEntity::getVotes).reversed());

        for(CommentEntity comm : searchComments){

            UserCommentDTO newComment = UserCommentDTO.builder()
                .idPost(comm.getPost().getId())
                .content(comm.getContent())
                .votes(comm.getVotes())
                .build();

            listAllCommentsUser.add(newComment);

        }

        return listAllCommentsUser;

        
    }


    @Override
    public List<CommentEntity> getCommentsByPostIdOrderByVotes(Long postId) {
        return commentRepository.findByPostIdOrderByVotesDesc(postId);
    }

    @Override
    public List<CommentEntity> getCommentsByPostIdOrderByNewest(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    @Override
    public List<CommentEntity> getCommentsByPostIdOrderByOldest(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

}