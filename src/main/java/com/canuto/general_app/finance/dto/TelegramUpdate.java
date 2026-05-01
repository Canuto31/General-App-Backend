package com.canuto.general_app.finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramUpdate {
    
    private Long updateId;
    private TelegramMessage message;
}
