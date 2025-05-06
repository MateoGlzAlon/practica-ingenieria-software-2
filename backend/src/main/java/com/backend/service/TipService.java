package com.backend.service;

import com.backend.persistence.entity.TipEntity;

import java.util.List;

public interface TipService {

    TipEntity findTipById(Long id);

    List<TipEntity> findTipsByPostId(Long id);
}
