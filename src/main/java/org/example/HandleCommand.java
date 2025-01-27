package org.example;

import org.telegram.telegrambots.meta.generics.TelegramClient;

// Наследуюсь для использования методов класса.
public class HandleCommand {
    private final TelegramClient telegramClient;
    public HandleCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void handleCommand(Long chatID, String command) {
        switch (command.toLowerCase()) { // для преобразования всех символов строки в нижний регистр
            case "/start":
                new SenderMessage(telegramClient).sendTextMessage(chatID, "Привет! Я тестовый бот. Используй /help для списка команд.");
                break;
            case "/help":
                new SenderMessage(telegramClient).sendTextMessage(chatID, "Доступные команды: \n/start - Начало работы \n/help - Справка");
                break;
            default:
                new SenderMessage(telegramClient).sendTextMessage(chatID, "Неизвестная команда. Используй /help для списка команд.");
        }
    }
}
