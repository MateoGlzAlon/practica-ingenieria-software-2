package com.backend.service.impl;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;
import com.backend.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
