package com.backend.persistence.inputDTO;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostVoteInputDTO {

    private Long postId;
    private Long userId;

}