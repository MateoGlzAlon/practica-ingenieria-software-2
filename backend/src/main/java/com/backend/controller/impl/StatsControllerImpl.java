package com.backend.controller.impl;

import com.backend.controller.StatsController;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@AllArgsConstructor
public class StatsControllerImpl implements StatsController {

    private final StatsService statsService;

    @Override
    @GetMapping("/community")
    public CommunityStatsDTO getCommunityStats() {
        return statsService.getCommunityStats();
    }
}
