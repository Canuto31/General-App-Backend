package com.canuto.general_app.finance.telegram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.telegram.dto.TelegramUpdateEntity;
import com.canuto.general_app.finance.telegram.repository.TelegramUpdateRepository;

@Service
public class TelegramUpdateService {
    
    @Autowired
    private TelegramUpdateRepository repository;

    public boolean isAlreadyProcessed(Long updateId) {
        return repository.existsByUpdateId(updateId);
    }

    public void markAsProcessed(Long updateId) {
        try {
            TelegramUpdateEntity entity = new TelegramUpdateEntity();
            entity.setUpdateId(updateId);
            repository.save(entity);
        } catch (Exception e) {
            
        }
        
    }
}
