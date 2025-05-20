package com.backend.controller;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stats")
public interface StatsController {

    @GetMapping("/community")
    CommunityStatsDTO getCommunityStats();

}
