package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.TipEntity;

public interface TipRepository extends JpaRepository<TipEntity, Long> {
}