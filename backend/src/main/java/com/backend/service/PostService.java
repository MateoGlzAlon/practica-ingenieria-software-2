package com.backend.service;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.inputDTO.PostInputDTO;

import java.util.List;

public interface PostService {

    PostEntity findPostById(Long id);

    List<FeedPostDTO> getFeedPosts();

    PostInputDTO getPostIndividual(Long id);
}


