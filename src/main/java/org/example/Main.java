package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        String botToken = "5384659247:AAG9CS7x4Dk_HRt6kDkzeuu9PWFRJAbhMDY";

        // Экземпляр, для дальнейшей регистрации бота.
        TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication();
        // Регистрация бота.
        try {
            application.registerBot(botToken, new MyAmazingBot());
            System.out.println("ПУСК");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }



    }
}