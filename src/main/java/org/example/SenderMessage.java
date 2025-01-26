package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

// Наследуемся, для получения метода "TelegramClient telegramClient = new......."
public class SenderMessage extends MyAmazingBot {
    // Создал обьект, с помощью которого возможно отправлять сообщения.

    // Конструктор.
//    public SenderMessage(TelegramClient telegramClient) {
//        this.telegramClient = telegramClient;
//    }

    public void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        // Вызываем метод у execute (для отправки), в ранее полученном обьекте через конструктор.
        try {
            telegramClient.execute(sendMessage);
            System.err.println("Отправил эхо пользователю с ID : " + sendMessage.getChatId());
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка отправки сообщения", e);
        }
    }

}
