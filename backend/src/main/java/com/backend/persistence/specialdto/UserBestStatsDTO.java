package com.backend.persistence.specialdto;

import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.ActivityOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class UserBestStatsDTO {

    private Long id;
    private String username;
    private Long totalContributions;

}