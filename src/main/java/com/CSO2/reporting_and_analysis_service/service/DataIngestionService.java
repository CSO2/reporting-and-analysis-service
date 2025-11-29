package com.CSO2.reporting_and_analysis_service.service;

import com.CSO2.reporting_and_analysis_service.dto.OrderCreatedEvent;
import com.CSO2.reporting_and_analysis_service.entity.DailySales;
import com.CSO2.reporting_and_analysis_service.entity.ProductPerformance;
import com.CSO2.reporting_and_analysis_service.repository.DailySalesRepository;
import com.CSO2.reporting_and_analysis_service.repository.ProductPerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataIngestionService {

    private final DailySalesRepository dailySalesRepository;
    private final ProductPerformanceRepository productPerformanceRepository;

    @Async
    @Transactional
    public void processOrderEvent(OrderCreatedEvent event) {
        // Update DailySales
        DailySales dailySales = dailySalesRepository.findByDate(event.getDate())
                .orElse(new DailySales(null, event.getDate(), java.math.BigDecimal.ZERO, 0));

        dailySales.setTotalRevenue(dailySales.getTotalRevenue().add(event.getTotalAmount()));
        dailySales.setTotalOrders(dailySales.getTotalOrders() + 1);
        dailySalesRepository.save(dailySales);

        // Update ProductPerformance
        for (OrderCreatedEvent.OrderItem item : event.getItems()) {
            ProductPerformance productPerformance = productPerformanceRepository.findByProductId(item.getProductId())
                    .orElse(new ProductPerformance(null, item.getProductId(), 0, 0, 0));

            productPerformance.setSales(productPerformance.getSales() + item.getQuantity());
            productPerformanceRepository.save(productPerformance);
        }
    }
}
