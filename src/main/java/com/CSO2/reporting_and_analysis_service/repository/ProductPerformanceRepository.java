package com.CSO2.reporting_and_analysis_service.repository;

import com.CSO2.reporting_and_analysis_service.entity.ProductPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPerformanceRepository extends JpaRepository<ProductPerformance, Long> {
    Optional<ProductPerformance> findByProductId(String productId);

    @Query("SELECT p FROM ProductPerformance p ORDER BY p.sales DESC")
    List<ProductPerformance> findTopSellingProducts();
}
