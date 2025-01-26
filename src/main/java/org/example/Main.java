package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        SenderMessage senderMessage = new SenderMessage();
        // Создаю обьект Класса, для того, что б передать в переменную токен.
        String botToken = new GetToken().token();

        // Экземпляр, для дальнейшей регистрации бота.
        TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication();
        // Регистрация бота.
        try {
            application.registerBot(botToken, new MyAmazingBot());
            System.out.println("ПУСК");
            senderMessage.sendTextMessage(249438024L, "Запустился босс.");
            // Остановка потока.
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            senderMessage.sendTextMessage(6119250690L, "Запустился Александр.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}