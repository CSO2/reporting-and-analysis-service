package com.CSO2.reporting_and_analysis_service.controller;

import com.CSO2.reporting_and_analysis_service.dto.response.DashboardMetricsDTO;
import com.CSO2.reporting_and_analysis_service.dto.response.TopProductDTO;
import com.CSO2.reporting_and_analysis_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardMetricsDTO> getDashboardMetrics(
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        if (start == null)
            start = LocalDate.now().minusDays(30);
        if (end == null)
            end = LocalDate.now();

        return ResponseEntity.ok(dashboardService.getAdminMetrics(start, end));
    }

    @GetMapping("/sales")
    public ResponseEntity<List<TopProductDTO>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(dashboardService.getTopSelling(limit));
    }

    @GetMapping("/inventory")
    public ResponseEntity<String> getInventoryReports() {
        // Placeholder for inventory reports as per requirements, logic not fully
        // defined in prompt
        return ResponseEntity.ok("Inventory Reports - To be implemented based on specific requirements");
    }
}
