package com.backend.controller;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.inputDTO.TipInputDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tips")
public interface TipController {

    @GetMapping("/{id}")
    TipEntity findTipById(@PathVariable Long id);

    @PostMapping("/send")   
    ResponseEntity<String> sendTip(@RequestBody TipInputDTO dto);

}