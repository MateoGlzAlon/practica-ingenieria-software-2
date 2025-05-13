package com.backend.service;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.repository.PostRepository;


public interface StatsService {


    CommunityStatsDTO getCommunityStats();

}
