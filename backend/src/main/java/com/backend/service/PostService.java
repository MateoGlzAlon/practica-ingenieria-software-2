package com.backend.service;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;

import java.util.List;

public interface PostService {

    PostEntity findPostById(Long id);

    List<FeedPostDTO> getFeedPosts(int page, int size, Long userId, List<String> tags);

    PostOutputDTO getPostIndividual(Long id);

    PostDetailsDTO getPostDetails(Long id);

    PostEntity createPost(PostInputDTO post);

}


