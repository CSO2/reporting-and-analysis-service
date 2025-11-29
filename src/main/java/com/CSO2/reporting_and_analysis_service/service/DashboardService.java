package com.CSO2.reporting_and_analysis_service.service;

import com.CSO2.reporting_and_analysis_service.dto.DashboardMetricsDTO;
import com.CSO2.reporting_and_analysis_service.dto.TopProductDTO;
import com.CSO2.reporting_and_analysis_service.entity.DailySales;

import com.CSO2.reporting_and_analysis_service.repository.DailySalesRepository;
import com.CSO2.reporting_and_analysis_service.repository.ProductPerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

        private final DailySalesRepository dailySalesRepository;
        private final ProductPerformanceRepository productPerformanceRepository;

        public DashboardMetricsDTO getAdminMetrics(LocalDate start, LocalDate end) {
                List<DailySales> sales = dailySalesRepository.findByDateBetween(start, end);

                BigDecimal totalRevenue = sales.stream()
                                .map(DailySales::getTotalRevenue)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                Integer totalOrders = sales.stream()
                                .mapToInt(DailySales::getTotalOrders)
                                .sum();

                // For now, we'll mock the trends and other data until we have historical
                // comparison logic
                // In a real scenario, we'd query the previous period to calculate change.

                return new DashboardMetricsDTO(
                                totalRevenue, "+12.5%", "up",
                                totalOrders, "+8.2%", "up",
                                12, "-3.1%", "down", // Mock pending RMAs
                                1847, "+15.8%", "up" // Mock active customers
                );
        }

        public List<TopProductDTO> getTopSelling(int limit) {
                return productPerformanceRepository.findTopSellingProducts().stream()
                                .limit(limit)
                                .map(p -> new TopProductDTO(p.getProductId(), p.getSales()))
                                .collect(Collectors.toList());
        }
}
