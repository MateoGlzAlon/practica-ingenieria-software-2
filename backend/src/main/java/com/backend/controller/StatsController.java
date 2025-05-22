package com.backend.controller;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.UserBestStatsDTO;
import com.backend.persistence.specialdto.PostHotQuestionsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("/stats")
public interface StatsController {

    @GetMapping("/community")
    CommunityStatsDTO getCommunityStats();

    @GetMapping("/top-users")
    List<UserBestStatsDTO> getTop3Users();

    @GetMapping("/hot-posts")
    List<PostHotQuestionsDTO> hotPosts();
}
