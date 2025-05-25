package com.backend.controller.impl;

import com.backend.controller.TipController;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.inputDTO.TipInputDTO;
import com.backend.service.TipService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping
    public ResponseEntity<String> sendTip(@RequestBody TipInputDTO dto) {
        try {
            tipService.sendTip(dto);
            return ResponseEntity.ok("Tip sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}