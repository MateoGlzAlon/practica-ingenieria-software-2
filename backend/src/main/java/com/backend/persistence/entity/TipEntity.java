package com.backend.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import lombok.*;

@Entity
@Table(name = "tips")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    @Column(nullable = false)
    private int amount;

    @Column(name = "created_at")
    private Date createdAt;
}