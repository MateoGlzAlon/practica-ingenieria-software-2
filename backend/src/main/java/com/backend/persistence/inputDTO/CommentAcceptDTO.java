package com.backend.persistence.inputDTO;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAcceptDTO {

    private Long commentId;
    private Long postId;
    private Long userId;

}