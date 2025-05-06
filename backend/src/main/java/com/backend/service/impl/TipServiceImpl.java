package com.backend.service.impl;

import com.backend.persistence.entity.TipEntity;
import com.backend.repository.TipRepository;
import com.backend.service.TipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;


    @Override
    public TipEntity findTipById(Long id) {
        return tipRepository.findById(id).orElse(null);
    }

    @Override
    public List<TipEntity> findTipsByPostId(Long id) {
        return tipRepository.findTipsByPostId(id);
    }

}