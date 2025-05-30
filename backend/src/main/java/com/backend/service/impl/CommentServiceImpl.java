package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.UserEntity;
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

import org.springframework.data.domain.Sort;

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
                //los votes se inician a 0
                .votes(0)
                .content(comment.getContent())
                //setteo el date actual
                .createdAt(new Date())
                .build();

        return commentRepository.save(newComment);
    }

    public CommentEntity acceptComment(CommentAcceptDTO comment){

        /*
        EXAMPLE FOR CHANGING ACCEPT STATUS
        {
            "userId":1,
            "postId":1,
            "commentId":1
        }

        */
        
        List<PostEntity> posts = postRepository.findPostsByUserId(comment.getUserId());
        
        boolean isValidUserVerification = false;

        for(PostEntity newPost : posts){
            if(Objects.equals(newPost.getId(), comment.getPostId())){
                isValidUserVerification = true;
                break;
            }
        }

        if(isValidUserVerification == false){
            return null;
        }
        

        CommentEntity updateComment = commentRepository.findById(comment.getCommentId()).get();

        if(updateComment == null){
            return null;
        }

        //change the value
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


}

