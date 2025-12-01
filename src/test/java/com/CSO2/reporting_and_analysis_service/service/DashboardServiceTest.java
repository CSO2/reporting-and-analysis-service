package com.CSO2.reporting_and_analysis_service.service;

import com.CSO2.reporting_and_analysis_service.dto.DashboardMetricsDTO;
import com.CSO2.reporting_and_analysis_service.dto.TopProductDTO;
import com.CSO2.reporting_and_analysis_service.entity.DailySales;
import com.CSO2.reporting_and_analysis_service.entity.ProductPerformance;
import com.CSO2.reporting_and_analysis_service.repository.DailySalesRepository;
import com.CSO2.reporting_and_analysis_service.repository.ProductPerformanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DashboardServiceTest {

    @Mock
    private DailySalesRepository dailySalesRepository;

    @Mock
    private ProductPerformanceRepository productPerformanceRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAdminMetrics_ShouldCalculateCorrectly() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();

        DailySales sales1 = new DailySales(1L, start, BigDecimal.valueOf(100), 2);
        DailySales sales2 = new DailySales(2L, end, BigDecimal.valueOf(200), 3);

        when(dailySalesRepository.findByDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(sales1, sales2));

        DashboardMetricsDTO metrics = dashboardService.getAdminMetrics(start, end);

        assertNotNull(metrics);
        assertEquals(BigDecimal.valueOf(300), metrics.getTotalRevenue());
        assertEquals(5, metrics.getTotalOrders());
    }

    @Test
    void getAdminMetrics_ShouldHandleEmptyList() {
        when(dailySalesRepository.findByDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        DashboardMetricsDTO metrics = dashboardService.getAdminMetrics(LocalDate.now(), LocalDate.now());

        assertNotNull(metrics);
        assertEquals(BigDecimal.ZERO, metrics.getTotalRevenue());
        assertEquals(0, metrics.getTotalOrders());
    }

    @Test
    void getTopSelling_ShouldReturnLimitedList() {
        ProductPerformance p1 = new ProductPerformance(1L, "prod1", 100, 0, 0);
        ProductPerformance p2 = new ProductPerformance(2L, "prod2", 80, 0, 0);
        ProductPerformance p3 = new ProductPerformance(3L, "prod3", 60, 0, 0);

        when(productPerformanceRepository.findTopSellingProducts())
                .thenReturn(Arrays.asList(p1, p2, p3));

        List<TopProductDTO> result = dashboardService.getTopSelling(2);

        assertEquals(2, result.size());
        assertEquals("prod1", result.get(0).getProductId());
        assertEquals("prod2", result.get(1).getProductId());
    }
}
