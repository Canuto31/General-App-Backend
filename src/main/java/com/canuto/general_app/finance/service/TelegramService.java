package com.canuto.general_app.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramService {
    
    private final String BOT_TOKEN = "8062287947:AAEADEXcIaV8N6QIw1QH_iHgBN6NHJ5PHwM";

    public void sentMessage(Long chatId, String text) {

        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

        RestTemplate restTemplate = new RestTemplate();

        String body = """
            {
                "chatId": %d,
                "text": "%s"
            }
        """.formatted(chatId, text);

        restTemplate.postForObject(url, body, String.class);
    }
}
