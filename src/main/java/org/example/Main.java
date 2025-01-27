package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Main {
    private static TelegramClient telegramClient;

    public static void main(String[] args) throws Exception {

        // Создаю объект Класса, для того, что б передать в переменную токен.
        String botToken = new GetToken().token();
        // Создаем клиент Telegram
        TelegramClient telegramClient = new OkHttpTelegramClient(botToken);  //

        // Экземпляр, для дальнейшей регистрации бота.

        // Регистрация бота.
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            application.registerBot(botToken, new MyAmazingBot());
            System.out.println("Бот запущен!");
            new SenderMessage(telegramClient).sendStartMessageForAdmin();
            Thread.currentThread().join();
        } catch (TelegramApiException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}