package com.canuto.general_app.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/telegram")
public class TelegramController {
    
    @PostMapping("/webhook")
    public ResponseEntity<?> receiveUpdate(@RequestBody String update) {
        System.out.println(update);
        return ResponseEntity.ok().build();
    }
    
}
