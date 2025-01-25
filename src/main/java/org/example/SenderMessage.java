package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SenderMessage {
    // Создал обьект, с помощью которого возможно отправлять сообщения.
    TelegramClient telegramClient;

    // Конструктор.
    public SenderMessage(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try {
            telegramClient.execute(sendMessage);
            System.out.println("Отправил : " + sendMessage.getChatId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
