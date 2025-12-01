package com.CSO2.reporting_and_analysis_service.controller;

import com.CSO2.reporting_and_analysis_service.dto.DashboardMetricsDTO;
import com.CSO2.reporting_and_analysis_service.dto.TopProductDTO;
import com.CSO2.reporting_and_analysis_service.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class AnalyticsControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private AnalyticsController analyticsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDashboardMetrics_ShouldReturnMetrics() {
        DashboardMetricsDTO mockMetrics = new DashboardMetricsDTO(
                BigDecimal.valueOf(1000), "+10%", "up",
                50, "+5%", "up",
                2, "-1%", "down",
                100, "+20%", "up");

        when(dashboardService.getAdminMetrics(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(mockMetrics);

        ResponseEntity<DashboardMetricsDTO> response = analyticsController.getDashboardMetrics(null, null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockMetrics, response.getBody());
    }

    @Test
    void getTopSellingProducts_ShouldReturnList() {
        List<TopProductDTO> mockList = Arrays.asList(
                new TopProductDTO("prod1", 100),
                new TopProductDTO("prod2", 80));

        when(dashboardService.getTopSelling(anyInt())).thenReturn(mockList);

        ResponseEntity<List<TopProductDTO>> response = analyticsController.getTopSellingProducts(10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("prod1", response.getBody().get(0).getProductId());
    }

    @Test
    void getInventoryReports_ShouldReturnPlaceholder() {
        ResponseEntity<String> response = analyticsController.getInventoryReports();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Inventory Reports - To be implemented based on specific requirements", response.getBody());
    }
}
