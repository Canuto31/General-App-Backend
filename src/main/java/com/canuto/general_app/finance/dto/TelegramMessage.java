package com.canuto.general_app.finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage {
    
    private Long messageId;
    private String text;
    private TelegramChat chat;
}
