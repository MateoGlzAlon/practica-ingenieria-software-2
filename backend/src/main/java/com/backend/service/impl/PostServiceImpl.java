package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.repository.PostRepository;
import com.backend.repository.TagRepository;
import com.backend.repository.PostImageRepository;
import com.backend.repository.UserRepository;
import com.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;


    @Override
    public PostEntity findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<FeedPostDTO> getFeedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostEntity> postsPage = postRepository.findAll(pageable);

        List<FeedPostDTO> feedPostDTOs = new ArrayList<>();
        for (PostEntity post : postsPage.getContent()) {
            String firstImageUrl = null;
            if (post.getImages() != null && !post.getImages().isEmpty()) {
                firstImageUrl = post.getImages().get(0).getImageUrl();
            }

            FeedPostDTO feedPostDTO = FeedPostDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .imageURL(firstImageUrl)
                    .likes(post.getLikes())
                    .state(post.getState())
                    .authorUsername(post.getUser().getUsername())
                    .commentCount(post.getComments().size())
                    .createdAt(post.getCreatedAt())
                    .content(post.getContent().substring(0,Math.min(100,post.getContent().length())))
                    .build();

            feedPostDTOs.add(feedPostDTO);
        }

        return feedPostDTOs;
    }


    @Override
    public PostOutputDTO getPostIndividual(Long id){
        PostEntity post = postRepository.findById(id).orElse(null);

        if (post == null) {
            return null;
        }

        return PostOutputDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getLikes())
                .build();
    }

    @Override
    public PostDetailsDTO getPostDetails(Long id) {

        PostEntity post = postRepository.findById(id).orElse(null);

        List<String> listImages = new ArrayList<>();

        for( PostImageEntity image : post.getImages()){

            listImages.add(image.getImageUrl());

        }


        PostDetailsDTO postDetails = PostDetailsDTO.builder()
                .id(post.getId())
                .author(post.getUser().getUsername())
                .postImages(listImages)
                .title(post.getTitle())
                .content(post.getContent())
                .votes(post.getLikes())
                .date(post.getCreatedAt())
                .state(post.getState())
                .build();


        return postDetails;
    }

    @Override
    public PostEntity createPost(PostInputDTO post) {

        /*
        {
            "title":"test2",
            "content":"this a test2",
            "userId": 1
            "tagId": 1,
            "imageLinks":[
                "https://placehold.co/600x400?text=Post90",
                "https://placehold.co/600x400?text=Post91"
            ]
        }
         */
        UserEntity user = userRepository.findById(post.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        TagEntity tag = tagRepository.findById(post.getTagId()).orElse(null);

        PostEntity newPost = PostEntity.builder()
                .user(user)
                .title(post.getTitle())
                .content(post.getContent())
                .tag(tag)
                .likes(0)
                .state("open")
                .createdAt(new Date())
                .images(new ArrayList<>()) // inicializamos vacío para luego agregar
                .comments(new ArrayList<>()) // si quisieras manejar comentarios también
                .build();

        // Convertir imageLinks a PostImageEntity
        if (post.getImageLinks() != null) {
            for (String link : post.getImageLinks()) {
                PostImageEntity image = new PostImageEntity();
                image.setImageUrl(link);
                image.setPost(newPost);
                newPost.getImages().add(image);
            }
        }

        return postRepository.save(newPost);
    }




}
