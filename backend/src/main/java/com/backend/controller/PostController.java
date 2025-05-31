package com.backend.controller;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import jakarta.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/posts")
public interface PostController {

    @GetMapping("/{id}")
    PostEntity findPostById(@PathVariable Long id);

    @GetMapping("/landingPageFeed")
    List<FeedPostDTO> getFeedPosts(int page, int size, List<String> tags, @RequestParam Long userId);

    @GetMapping("/focus/{id}")
    PostOutputDTO getPostIndividual(@PathVariable Long id);

    @GetMapping("/details/{id}")
    PostDetailsDTO getPostDetails(@PathVariable Long id);

    @PostMapping
    PostEntity createPost(@RequestBody PostInputDTO post);

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id);

}
