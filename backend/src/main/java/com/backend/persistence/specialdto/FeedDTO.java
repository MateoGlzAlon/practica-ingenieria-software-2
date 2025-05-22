package com.backend.persistence.specialdto;

import com.backend.persistence.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDTO {

    List<FeedPostDTO> posts;

}
