package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

// Наследуюсь для использования методов класса.
public class HandleCommand {
    // Приватное поле для хранения клиента Telegram
    // (ключевое слово final означает, что его нельзя изменить после инициализации).
    private final TelegramClient telegramClient;
    // Один объект SenderMessage при инициализации класса HandleCommand и использовать его для всех сообщений.
    private final SenderMessage sender;
    // Конструктор класса. Принимает клиент Telegram при создании объекта
    // Это пример "внедрения зависимости" (Dependency Injection
    public HandleCommand(TelegramClient telegramClient, SenderMessage sender) {
        this.telegramClient = telegramClient;
        this.sender = sender;
    }

    public void handleCommand(Long chatID, String command) {
        switch (command.toLowerCase()) { // для преобразования всех символов строки в нижний регистр.
            case "/start":
                sender.sendTextMessageAndCallback(chatID, "Привет! Я тестовый бот. \nИспользуйте /help для списка команд." +
                        "\nВыберите из списка ниже маркетплейс.");
                break;
            case "/help":
                sender.sendTextMessage(chatID, "Доступные команды: \n/start - Начало работы \n/help - Справка");
                break;
            default:
                sender.sendTextMessage(chatID, "Неизвестная команда. Используй /help для списка команд.");
        }
    }
}
