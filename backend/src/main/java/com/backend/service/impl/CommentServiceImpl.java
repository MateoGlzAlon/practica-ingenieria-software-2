package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
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
    public List<CommentOutputDTO> findCommentsOfAPost(Long id) {
        List<CommentEntity> comments = commentRepository.findByPostId(id);

        List<CommentOutputDTO> listCommentsOutput = new ArrayList<>();

        for(CommentEntity newComment : comments){

            CommentOutputDTO auxToComment = CommentOutputDTO.builder()
                .id(newComment.getId())
                .author(newComment.getUser().getUsername())
                .authorProfilePicture(newComment.getUser().getAvatarUrl())
                .createdAt(newComment.getCreatedAt())
                .content(newComment.getContent())
                .votes(newComment.getLikes())
                .commentCount(0) // change this later
                .accepted(false)
                .build();

            listCommentsOutput.add(auxToComment);

        }

        return listCommentsOutput;

        
    }

    @Override
    public CommentEntity createComment(CommentInputDTO comment){

        /*
        EXAMPLE OF REQUEST:
        {
        "post_id":28,
        "content":"WOW!!!!!!!!!! NICE ONE LOL"
        }
        */

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

