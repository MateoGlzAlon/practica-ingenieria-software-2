package com.backend.controller;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.specialdto.FeedDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/posts")
public interface PostController {
    
    //merge this one and the "getPostIndividual()" if its not used
    @GetMapping("/{id}")
    PostEntity findPostById(@PathVariable Long id);

    @GetMapping("/landingPageFeed")
    List<FeedPostDTO> getFeedPosts(int page, int size);

    @GetMapping("/focus/{id}")
    PostInputDTO getPostIndividual(@PathVariable Long id);

    @GetMapping("/details/{id}")
    PostDetailsDTO getPostDetails(@PathVariable Long id);

}
