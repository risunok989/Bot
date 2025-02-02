package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Handlers.HandleCommand;
import org.example.Handlers.HandleMessage;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    //---------------------------------------------------------------------------//
    //    Класс для запуска и регистрации бота.                                  //
    //---------------------------------------------------------------------------//

    public static void main(String[] args) throws Exception {
        String logPath = Paths.get("logs/app.log").toAbsolutePath().toString();
        System.out.println("Ожидаемый путь к логам: " + logPath);
        ExecutorService executor = Executors.newCachedThreadPool(); // Создаем пул
        // 1. Получаем токен бота из файла config.properties
        String botToken = new GetToken().token();
        // 2. Создаем клиент для взаимодействия с Telegram API
        TelegramClient telegramClient = new OkHttpTelegramClient(botToken);  //
        // 3. Регистрируем бота и запускаем Long Polling
        try (TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication()) {
            // 4. Регистрация основного бота             // Передаем пул в конструктор
            MyAmazingBot myAmazingBot = new MyAmazingBot(executor, telegramClient, new HandleCommand(telegramClient), new HandleMessage(telegramClient, new HandleCommand(telegramClient)));
            // 4.2 Передаю зависимости в MyAmazingBot через конструктор!
            application.registerBot(botToken, myAmazingBot);
            logger.info("Бот зарегистрирован и запущен.");
            // 5. Инициализируем сервис для отправки сообщений, отправляю уведомление админу.
            new SenderUserMessage(telegramClient).sendStartMessageForAdmin();
            // Регистрируем обработчик завершения работы
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nПолучен сигнал завершения работы...");
                myAmazingBot.shutdown(); // Вызываем shutdown() при завершении приложения
            }));
            // 6. Блокируем главный поток, чтобы приложение не завершилось (работает и без этого!)
            Thread.currentThread().join();
        } catch (TelegramApiException | InterruptedException e) {
            logger.error("Ошибка Telegram API: {}", e.getMessage(), e);
                        System.err.println("❌ Фатальная ошибка при запуске:");

            // 6.1. Отправляем админу сообщение об ошибке
            SenderUserMessage senderUserMessage = new SenderUserMessage(telegramClient);
            senderUserMessage.sendTextMessage(new GetToken().adminID(), "Ошибка запуска: " + e.getMessage());
        }

    }

}