package com.canuto.general_app.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.dto.TelegramUpdate;
import com.canuto.general_app.finance.model.Expense;
import com.canuto.general_app.finance.parser.TextParserService;
import com.canuto.general_app.finance.service.FinanceService;
import com.canuto.general_app.finance.service.TelegramService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    @Autowired
    private TextParserService parserService;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private TelegramService telegramService;
    
    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveUpdate(@RequestBody TelegramUpdate update) {

        if (update.getMessage() != null) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChat().getId();

            System.out.println("ChatId: " + chatId);
            System.out.println("Message: " + text);

            List<Expense> expenses = parserService.parseMultipleExpenses(text);
            for (Expense expense : expenses) {
                financeService.saveExpense(expense);
            }

            telegramService.sentMessage(chatId, "Saved " + expenses.size() + "expenses ✅");
        }

        return ResponseEntity.ok().build();
    }
    
}
