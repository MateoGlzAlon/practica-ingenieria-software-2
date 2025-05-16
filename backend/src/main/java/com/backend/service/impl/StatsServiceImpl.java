package com.backend.service.impl;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.UserBestStatsDTO;
import com.backend.persistence.specialdto.PostHotQuestionsDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;
import com.backend.persistence.entity.PostEntity;
import com.backend.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import java.util.*;


@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    @Override
    public CommunityStatsDTO getCommunityStats() {

        return CommunityStatsDTO.builder()
                .users(userRepository.count())
                .answers(commentRepository.count())
                .questions(postRepository.count())
                .build();
    }

    
    @Override
    public List<UserBestStatsDTO> getTop3Users() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Object[]> raw = userRepository.findTopUsersByActivity(pageable);

        return raw.stream()
            .map(row -> new UserBestStatsDTO(
                (Long)row[0],
                (String)row[1],
                ((Number)row[2]).longValue()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<PostHotQuestionsDTO> hotPosts(){

        //get the 3 most recent questions from the db
        Pageable pageable = PageRequest.of(0, 3, Sort.by("createdAt").descending());
        Page<PostEntity> postsPage = postRepository.findAll(pageable);

        List<PostHotQuestionsDTO> totalPosts = new ArrayList<>();

        for(PostEntity post : postsPage.getContent()){

            PostHotQuestionsDTO newPost = PostHotQuestionsDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .build();

            totalPosts.add(newPost);

        }

        return totalPosts;

    }
}
