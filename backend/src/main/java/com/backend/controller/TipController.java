package com.backend.controller;

import com.backend.persistence.entity.TipEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tips")
public interface TipController {

    @GetMapping("/{id}")
    TipEntity findTipById(@PathVariable Long id);

}