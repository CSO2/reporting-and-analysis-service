package com.CSO2.reporting_and_analysis_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardMetricsDTO {
    private BigDecimal totalRevenue;
    private String revenueChange;
    private String revenueTrend;
    private Integer totalOrders;
    private String ordersChange;
    private String ordersTrend;
    private Integer pendingRMAs;
    private String rmaChange;
    private String rmaTrend;
    private Integer activeCustomers;
    private String activeCustomersChange;
    private String activeCustomersTrend;
}
