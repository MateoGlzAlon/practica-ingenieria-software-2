package com.backend.controller;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.inputDTO.PostCreationDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/posts")
public interface PostController {

    @GetMapping("/{id}")
    PostEntity findPostById(@PathVariable Long id);

    @GetMapping("/landingPageFeed")
    List<FeedPostDTO> getFeedPosts(int page, int size);

    @GetMapping("/focus/{id}")
    PostInputDTO getPostIndividual(@PathVariable Long id);

    @GetMapping("/details/{id}")
    PostDetailsDTO getPostDetails(@PathVariable Long id);


    // create post
    @PostMapping("/create")
    PostEntity createPost(@RequestBody PostCreationDTO post);

}
