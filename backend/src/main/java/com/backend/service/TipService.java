package com.backend.service;

import com.backend.persistence.entity.TipEntity;

public interface TipService {

    TipEntity findTipById(Long id);

}
