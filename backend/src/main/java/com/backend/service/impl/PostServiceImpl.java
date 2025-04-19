package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.repository.PostRepository;
import com.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        List<FeedPostDTO> feedPostDTOs = new ArrayList<>();

        for (PostEntity post : posts) {
            String firstImageUrl = null;
            if (post.getImages() != null && !post.getImages().isEmpty()) {
                firstImageUrl = post.getImages().get(0).getImageUrl();
            }

            //data
            //Timestamp createdAtTimestamp = Timestamp.valueOf(post.getCreatedAt());
            int commentCount = (post.getComments() != null) ? post.getComments().size() : 0;

            String formattedDate = post.getCreatedAt().format(formatter);
            
            //add author, content, createdAt
            FeedPostDTO feedPostDTO = FeedPostDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getUser().getUsername())
                    .createdAt(formattedDate) // instead of a Timestamp, return String
                    .comments(commentCount)
                    .imageURL(firstImageUrl)
                    .likes(post.getLikes())
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
                .likes(post.getLikes())
                .build();
    }

}
