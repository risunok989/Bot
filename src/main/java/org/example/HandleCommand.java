package org.example;

public class HandleCommand extends SenderMessage {
    public HandleCommand() {
    }

    public void handleCommand(Long chatID, String command) {
        switch (command.toLowerCase()) {
            case "/start":
                sendTextMessage(chatID, "Привет! Я тестовый бот. Используй /help для списка команд.");
                break;
            case "/help":
               sendTextMessage(chatID, "Доступные команды: \n/start - Начало работы \n/help - Справка");
                break;
            default:
                sendTextMessage(chatID, "Неизвестная команда. Используй /help для списка команд.");
        }
    }
}
