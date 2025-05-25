package com.backend.persistence.inputDTO;

import lombok.Data;

@Data
public class TipInputDTO {
    private Long senderId;
    private Long receiverId;
    private int amount;
    private Long postId;  
    private Long commentId;
}
