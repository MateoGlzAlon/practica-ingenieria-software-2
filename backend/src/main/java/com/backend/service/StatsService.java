package com.backend.service;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.UserBestStatsDTO;
import com.backend.repository.PostRepository;

import java.util.*;


public interface StatsService {

    CommunityStatsDTO getCommunityStats();

    List<UserBestStatsDTO> getTop3Users();

}
