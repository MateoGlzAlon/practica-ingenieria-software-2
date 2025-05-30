package com.backend.persistence.inputDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipInputDTO {
    private Long senderId;
    private Long receiverId;
    private int amount;
}
