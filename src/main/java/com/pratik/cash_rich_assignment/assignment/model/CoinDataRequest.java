package com.pratik.cash_rich_assignment.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coin_data_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinDataRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String response;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
