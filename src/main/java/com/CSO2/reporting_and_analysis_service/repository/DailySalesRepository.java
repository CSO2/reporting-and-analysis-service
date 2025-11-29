package com.CSO2.reporting_and_analysis_service.repository;

import com.CSO2.reporting_and_analysis_service.entity.DailySales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailySalesRepository extends JpaRepository<DailySales, Long> {
    Optional<DailySales> findByDate(LocalDate date);

    @Query("SELECT d FROM DailySales d WHERE d.date BETWEEN :start AND :end")
    List<DailySales> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
