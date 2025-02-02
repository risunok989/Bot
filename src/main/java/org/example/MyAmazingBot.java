package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Handlers.HandleCallback;
import org.example.Handlers.HandleCommand;
import org.example.Handlers.HandleMessage;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;

//---------------------------------------------------------------------------//
//    Класс для обработки update пользователя.                               //
//---------------------------------------------------------------------------//

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
    private static final Logger logger = LogManager.getLogger(MyAmazingBot.class);

    private ExecutorService executor; // Изменяем тип пула
    private final Long ADMIN_ID = 249438024L;
    private final Long ALEX_ID = 6119250690L;
    private final Long OLGA_ID = 645409728L;
    private final TelegramClient telegramClient;
    private final HandleCommand handleCommand;
    private final HandleMessage handleMessage;
    // Обработчик команд (паттерн "Стратегия" для разделения логики)

    // Конструктор класса
    public MyAmazingBot(ExecutorService executor, TelegramClient telegramClient, HandleCommand handleCommand, HandleMessage handleMessage) {
        // Инициализация Telegram-клиента с токеном из config.properties
        // Передача зависимостей через конструктор (DI)
        this.telegramClient = telegramClient;
        this.handleCommand = handleCommand;
        this.handleMessage = handleMessage;
        // Создаем CachedThreadPool вместо FixedThreadPool
        this.executor = executor; // <-- Основное изменение здесь
        logger.info("CachedThreadPool создан. Готов к работе!");
    }


    //Переопределил главный метод в который приходят все обновления, т.к класс имплеминитрует другой класс.
    @Override
    public void consume(Update update) {
        // 3. Отправляем задачу в пул потоков
        executor.execute(() -> {
            try {
                processUpdate(update);
            } catch (Exception e) {
                logger.error("Ошибка в потоке: {} {}", Thread.currentThread().getName(), e.getMessage());
            }
        });

    }

    // 4. Выносим логику обработки в отдельный метод
    void processUpdate(Update update) {
        logger.info("Начало обработки Update: {}", update.getUpdateId());
        if (update.hasCallbackQuery()) {
            // 5. Обработка нажатий на кнопки
            HandleCallback.handle(update, telegramClient);
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            handleMessage.handleTextMessage(message);
        }
        logger.info("Завершение обработки Update: {}", update.getUpdateId());
    }

    // 6. Метод для корректного завершения работы
    public void shutdown() {
        System.out.println("Завершаем работу пула...");
        executor.shutdown(); // Прекращаем принимать новые задачи

        try {
            // Даем 30 секунд на завершение текущих задач
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.err.println("Принудительное завершение!");
                executor.shutdownNow(); // Прерываем все задачи
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            logger.error("Ошибка останки бота: {} {}", Thread.currentThread().getName(), e.getMessage());
        }
    }


    // Редактирую клавиатуру и вывожу новый текст.
    public void callbackEditMessage(Update update) {
        System.out.println(update.getCallbackQuery().getData());
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        if (call_data.equals("Wildberries_update")) {
            String answer = "Updated message text Wildberries";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();
            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (call_data.equals("Ozon_update")) {
            String answer = "Updated message text Ozon";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();
            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    // Обработка нажатий на inline-кнопки
    private void handleCallbackQuery(Update update) {
        // Получаем данные из callback (то что было указано в кнопке)
        String callbackData = update.getCallbackQuery().getData();
        // Идентификатор чата, откуда пришло нажатие
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        System.out.println("Received callback: " + callbackData + " from: " + chatId);

        // Отправляем подтверждение обработки
        new SenderUserMessage(telegramClient).sendTextMessage(chatId, "Handled callback: " + callbackData);
    }
}
