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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

}
