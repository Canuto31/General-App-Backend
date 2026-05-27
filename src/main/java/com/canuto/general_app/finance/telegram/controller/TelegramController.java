package com.canuto.general_app.finance.telegram.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.parser.TextParserService;
import com.canuto.general_app.finance.expense.service.FinanceService;
import com.canuto.general_app.finance.telegram.dto.TelegramUpdate;
import com.canuto.general_app.finance.telegram.service.TelegramCommandService;
import com.canuto.general_app.finance.telegram.service.TelegramService;
import com.canuto.general_app.finance.telegram.service.TelegramUpdateService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    /*
     * @Autowired
     * private TextParserService parserService;
     * 
     * @Autowired
     * private FinanceService financeService;
     */

    private TelegramService telegramService;
    private TelegramUpdateService updateService;
    private TelegramCommandService telegramCommandService;

    public TelegramController(
            TelegramService telegramService,
            TelegramUpdateService updateService,
            TelegramCommandService telegramCommandService) {
        this.telegramService = telegramService;
        this.updateService = updateService;
        this.telegramCommandService = telegramCommandService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveUpdate(@RequestBody TelegramUpdate update) {

        Long updateId = update.getUpdateId();

        if (updateService.isAlreadyProcessed(updateId)) {
            return ResponseEntity.ok().build();
        }

        try {
            if (update.getMessage() != null) {
                String text = update.getMessage().getText();
                Long chatId = update.getMessage().getChat().getId();

                System.out.println("ChatId: " + chatId);
                System.out.println("Message: " + text);

                String response = telegramCommandService.process(text);

                telegramService.sentMessage(chatId, response);

                updateService.markAsProcessed(updateId);
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (update.getMessage() != null) {

                Long chatId = update.getMessage().getChat().getId();

                telegramService.sentMessage(
                        chatId,
                        "Error processing command."
                );
            }
        }

        return ResponseEntity.ok().build();
    }

}
