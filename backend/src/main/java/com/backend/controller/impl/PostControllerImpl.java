package com.backend.controller.impl;

import com.backend.controller.PostController;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.persistence.inputDTO.PostCreationDTO;
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
            @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getFeedPosts(page, size);
    }


    @Override
    @GetMapping("/focus/{id}")
    public PostInputDTO getPostIndividual(@PathVariable Long id) {
        return postService.getPostIndividual(id);
    }

    @Override
    @GetMapping("/details/{id}")
    public PostDetailsDTO getPostDetails(@PathVariable Long id) {

        PostDetailsDTO postDetailsDTO = postService.getPostDetails(id);

        return postDetailsDTO;
    }

    @PostMapping("/create")
    public PostEntity createPost(@RequestBody PostCreationDTO post){
        return postService.createPost(post);
    }

}

