package com.backend.persistence.specialdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatsDTO {
    private Long users;
    private Long questions;
    private Long answers;
}
