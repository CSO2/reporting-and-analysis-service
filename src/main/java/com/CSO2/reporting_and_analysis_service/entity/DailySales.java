package com.CSO2.reporting_and_analysis_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "daily_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    private BigDecimal totalRevenue = BigDecimal.ZERO;

    private Integer totalOrders = 0;
}
