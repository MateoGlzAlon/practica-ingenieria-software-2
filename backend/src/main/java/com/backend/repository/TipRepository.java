package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.TipEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TipRepository extends JpaRepository<TipEntity, Long> {

    @Query("SELECT t FROM TipEntity t WHERE t.sender.id = :userId")
    List<TipEntity> findTipsSentByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM TipEntity t WHERE t.receiver.id = :userId")
    List<TipEntity> findTipsReceivedByUserId(@Param("userId") Long userId);

}