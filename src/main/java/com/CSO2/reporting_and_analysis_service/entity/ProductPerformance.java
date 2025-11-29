package com.CSO2.reporting_and_analysis_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productId;

    private Integer views = 0;

    private Integer sales = 0;

    private Integer cartAdds = 0;
}
