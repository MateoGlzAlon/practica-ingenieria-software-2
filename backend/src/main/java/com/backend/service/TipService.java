package com.backend.service;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.inputDTO.TipInputDTO;

public interface TipService {

    TipEntity findTipById(Long id);
    void sendTip(TipInputDTO dto);

}
