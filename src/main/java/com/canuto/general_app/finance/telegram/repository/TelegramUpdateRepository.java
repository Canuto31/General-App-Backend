package com.canuto.general_app.finance.telegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.telegram.dto.TelegramUpdateEntity;

@Repository
public interface TelegramUpdateRepository extends JpaRepository<TelegramUpdateEntity, Long>{
    
    boolean existsByUpdateId(Long updateId);
}
