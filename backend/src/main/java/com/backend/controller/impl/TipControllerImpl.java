package com.backend.controller.impl;

import com.backend.controller.TipController;
import com.backend.persistence.entity.TipEntity;
import com.backend.service.TipService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tips")
@AllArgsConstructor
public class TipControllerImpl implements TipController {

    private final TipService tipService;

    @Override
    @GetMapping("/{id}")
    public TipEntity findTipById(@PathVariable Long id) {
        return tipService.findTipById(id);
    }

    @Override
    public List<TipEntity> findTipsByPostId(Long id) {
        return tipService.findTipsByPostId(id);
    }

}