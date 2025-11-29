package com.CSO2.reporting_and_analysis_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricsDTO {
    private BigDecimal totalRevenue;
    private String revenueChange;
    private String revenueTrend; // "up" or "down"

    private Integer newOrders;
    private String newOrdersChange;
    private String newOrdersTrend;

    private Integer pendingRMAs;
    private String pendingRMAsChange;
    private String pendingRMAsTrend;

    private Integer activeCustomers;
    private String activeCustomersChange;
    private String activeCustomersTrend;
}
