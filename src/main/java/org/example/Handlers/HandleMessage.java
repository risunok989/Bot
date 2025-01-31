package org.example.Handlers;

import org.example.SenderUserMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class HandleMessage {
    private final TelegramClient telegramClient;

    public HandleMessage(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }
    // Метод для обработки сообщений разных типов


    public void handleMessage(Message message) {
        // Получаем идентификатор чата для ответа

        // Второй уровень проверки: тип содержимого.
        if (message.hasText()) {
            // Текстовые сообщения
            handleTextMessage(message);
        } else if (message.hasPhoto()) {
            // Сообщения с фотографиями
            //TODO
        } else if (message.hasDocument()) {
            // Сообщения с документами
            //TODO
        } else {
            // Все остальные сообщения.
            //TODO
        }
    }

    // Обработка текстовых сообщений
    public void handleTextMessage(Message message) {
        SenderUserMessage senderUserMessage = new SenderUserMessage(telegramClient);
        Long chatId = message.getChatId();
        String command = message.getText();

        // Если текс начинается с "/" - это команда
        if (command.startsWith("/")) {
            new HandleCommand(telegramClient).handleCommand(chatId, command);
        } else {
            // Иначе эхо ответ
            senderUserMessage.sendTextMessage(chatId, command);
//            sender.sendTextMessage(chatId, command);
        }
    }
}
