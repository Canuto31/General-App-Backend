package com.canuto.general_app.finance.projection.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.projection.model.MonthlyStatus;

@Repository
public interface MonthlyStatusRepository extends JpaRepository<MonthlyStatus, Long>{
    
    Optional<MonthlyStatus> findByMonthAndYear(Integer month, Integer year);
}
