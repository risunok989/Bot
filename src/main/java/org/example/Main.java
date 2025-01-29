package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Main {
    //---------------------------------------------------------------------------//
    //    Класс для запуска и регистрации бота.                                  //
    //---------------------------------------------------------------------------//

    public static void main(String[] args) throws Exception {


        // 1. Получаем токен бота из файла config.properties
        String botToken = new GetToken().token();
        // 2. Создаем клиент для взаимодействия с Telegram API
        TelegramClient telegramClient = new OkHttpTelegramClient(botToken);  //
        // 3. Регистрируем бота и запускаем Long Polling
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            // 4. Регистрация основного бота
            application.registerBot(botToken, new MyAmazingBot(new SenderMessage(telegramClient)));
            System.out.println("Бот запущен!");
            // 5. Инициализируем сервис для отправки сообщений, отправляю уведомление админу.
            new SenderMessage(telegramClient).sendStartMessageForAdmin();
            // 6. Блокируем главный поток, чтобы приложение не завершилось (работает и без этого!)
            Thread.currentThread().join();
        } catch (TelegramApiException | InterruptedException e) {
            System.err.println("❌ Фатальная ошибка при запуске:");
            e.printStackTrace();

            // 6.1. Отправляем админу сообщение об ошибке
            new SenderMessage(telegramClient).sendTextMessage(249438024L, "Ошибка запуска: " + e.getMessage());
        }

    }

}