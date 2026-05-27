package com.canuto.general_app.finance.telegram.service;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.telegram.enums.TelegramCommandType;

@Service
public class TelegramCommandResolver {
    
    public TelegramCommandType resolve(String text) {
        if (text == null || text.isBlank()) {
            return TelegramCommandType.UNKNOWN;
        }

        String lowerText = text.toLowerCase().trim();

        if (lowerText.startsWith("paid ")) {
            return TelegramCommandType.PAY_RECURRING;
        }

        if (lowerText.startsWith("received ")) {
            return TelegramCommandType.RECEIVE_INCOME;
        }

        if (lowerText.equals("summary")) {
            return TelegramCommandType.SUMMARY;
        }

        if (lowerText.equals("balance")) {
            return TelegramCommandType.BALANCE;
        }

        if (lowerText.equals("pending")) {
            return TelegramCommandType.PENDING;
        }

        if (lowerText.equals("help")) {
            return TelegramCommandType.HELP;
        }

        return TelegramCommandType.EXPENSE;
    }
}
