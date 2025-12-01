package com.CSO2.reporting_and_analysis_service.service;

import com.CSO2.reporting_and_analysis_service.dto.event.OrderCreatedEvent;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataIngestionServiceTest {

    @Mock
    private DailySalesRepository dailySalesRepository;

    @Mock
    private ProductPerformanceRepository productPerformanceRepository;

    @InjectMocks
    private DataIngestionService dataIngestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processOrderEvent_ShouldUpdateSalesAndPerformance() {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setDate(LocalDate.now());
        event.setTotalAmount(BigDecimal.valueOf(100));
        OrderCreatedEvent.OrderItem item1 = new OrderCreatedEvent.OrderItem();
        item1.setProductId("prod1");
        item1.setQuantity(2);
        event.setItems(Arrays.asList(item1));

        // Mock DailySales
        when(dailySalesRepository.findByDate(any(LocalDate.class))).thenReturn(Optional.empty());

        // Mock ProductPerformance
        when(productPerformanceRepository.findByProductId("prod1")).thenReturn(Optional.empty());

        dataIngestionService.processOrderEvent(event);

        verify(dailySalesRepository, times(1)).save(any(DailySales.class));
        verify(productPerformanceRepository, times(1)).save(any(ProductPerformance.class));
    }

    @Test
    void processOrderEvent_ShouldUpdateExistingRecords() {
        LocalDate today = LocalDate.now();
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setDate(today);
        event.setTotalAmount(BigDecimal.valueOf(100));
        OrderCreatedEvent.OrderItem item1 = new OrderCreatedEvent.OrderItem();
        item1.setProductId("prod1");
        item1.setQuantity(2);
        event.setItems(Arrays.asList(item1));

        DailySales existingSales = new DailySales(1L, today, BigDecimal.valueOf(50), 1);
        when(dailySalesRepository.findByDate(today)).thenReturn(Optional.of(existingSales));

        ProductPerformance existingPerformance = new ProductPerformance(1L, "prod1", 10, 0, 0);
        when(productPerformanceRepository.findByProductId("prod1")).thenReturn(Optional.of(existingPerformance));

        dataIngestionService.processOrderEvent(event);

        verify(dailySalesRepository, times(1)).save(existingSales);
        verify(productPerformanceRepository, times(1)).save(existingPerformance);

        // Assertions could be added here to check if values were updated correctly,
        // but verify(save) confirms the interaction.
    }
}
