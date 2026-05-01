package com.canuto.general_app.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.dto.TelegramUpdate;
import com.canuto.general_app.finance.parser.TextParserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    @Autowired
    private TextParserService parserService;
    
    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveUpdate(@RequestBody TelegramUpdate update) {

        if (update.getMessage() != null) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChat().getId();

            System.out.println("ChatId: " + chatId);
            System.out.println("Message: " + text);

            parserService.parseMultipleExpenses(text);
        }

        return ResponseEntity.ok().build();
    }
    
}
