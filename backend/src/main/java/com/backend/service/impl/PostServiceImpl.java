package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.repository.PostRepository;
import com.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    @Override
    public PostEntity findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<FeedPostDTO> getFeedPosts() {
        List<PostEntity> posts = postRepository.findAll();

        List<FeedPostDTO> feedPostDTOs = new ArrayList<>();

        for (PostEntity post : posts) {
            String firstImageUrl = null;
            if (post.getImages() != null && !post.getImages().isEmpty()) {
                firstImageUrl = post.getImages().get(0).getImageUrl();
            }

            FeedPostDTO feedPostDTO = FeedPostDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .imageURL(firstImageUrl)
                    .votes(post.getVotes())
                    .state("TO-DO")
                    .build();

            feedPostDTOs.add(feedPostDTO);
        }

        return feedPostDTOs;
    }

    @Override
    public PostInputDTO getPostIndividual(Long id){
        PostEntity post = postRepository.findById(id).orElse(null);

        if (post == null) {
            return null;
        }

        return PostInputDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .votes(post.getVotes())
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
                .votes(post.getVotes())
                .date(post.getCreatedAt())
                .build();


        return postDetails;
    }

}
