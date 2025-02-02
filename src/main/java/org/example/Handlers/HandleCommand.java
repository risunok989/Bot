package org.example.Handlers;

import org.example.Main;
import org.example.MyAmazingBot;
import org.example.SenderUserMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

//---------------------------------------------------------------------------//
//    Класс для обработки команд пользователя (/start, /help.... от update). //
//---------------------------------------------------------------------------//

public class HandleCommand {

    private final TelegramClient telegramClient;

    // Конструктор класса. Принимает клиент Telegram при создании объекта
    // Это пример "внедрения зависимости" (Dependency Injection)
    public HandleCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void handleCommand(Long chatID, String command) {
        SenderUserMessage senderUserMessage = new SenderUserMessage(telegramClient);
        switch (command.toLowerCase()) { // для преобразования всех символов строки в нижний регистр.
            case "/start":
                senderUserMessage.createPlatformKeyboard(chatID, "Привет! Я тестовый бот. " +
                        "\nИспользуйте /help для списка команд." + "\nВыберите из списка ниже маркетплейс.");
                break;
            case "/help":
                senderUserMessage.sendTextMessage(chatID, "Доступные команды: \n/start - Начало работы " +
                        "\n/help - Справка");
                break;
                case "/stop":

            default:
                senderUserMessage.sendTextMessage(chatID, "Неизвестная команда. " +
                        "\nИспользуй /help для списка команд.");
        }
    }
}
