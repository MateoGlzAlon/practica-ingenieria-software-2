package com.backend.controller.impl;

import com.backend.controller.PostController;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping("/{id}")
    public PostEntity findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    @GetMapping("/landingPageFeed")
    public List<FeedPostDTO> getFeedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Long userId
    ) {

        return postService.getFeedPosts(page, size, userId, tags);
    }


    @Override
    @GetMapping("/focus/{id}")
    public PostOutputDTO getPostIndividual(@PathVariable Long id) {
        return postService.getPostIndividual(id);
    }

    @Override
    @GetMapping("/details/{id}")
    public PostDetailsDTO getPostDetails(@PathVariable Long id) {
        return postService.getPostDetails(id);
    }

    @PostMapping
    public PostEntity createPost(@RequestBody PostInputDTO post){
        return postService.createPost(post);
    }

}

