package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.toIntExact;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
    private final Long ADMIN_ID = 249438024L;
    private final Long ALEX_ID = 6119250690L;
    private final Long OLGA_ID = 645409728L;

    public Long getADMIN_ID() {
        return ADMIN_ID;
    }

    public Long getALEX_ID() {
        return ALEX_ID;
    }

    public Long getOLGA_ID() {
        return OLGA_ID;
    }

    // Обработчик команд (паттерн "Стратегия" для разделения логики)
    // Клиент для взаимодействия с Telegram API.
    // Handler для взаимодействия с HandleCommand.
    // Sender для взаимодействия с SenderMessage.
    private final TelegramClient telegramClient;
    private final HandleCommand commandHandler;
    private final SenderMessage sender;

    // Конструктор класса
    public MyAmazingBot(SenderMessage sender) {
        this.sender = sender;
        // Инициализация Telegram-клиента с токеном из config.properties
        this.telegramClient = new OkHttpTelegramClient(new GetToken().token());
        this.commandHandler = new HandleCommand(telegramClient, new SenderMessage(telegramClient));
    }

    //Переопределил главный метод в который приходят все обновления, т.к класс имплеминитрует другой класс.
    @Override
    public void consume(Update update) {
        try {
            // Первый уровень проверки.
            if (update.hasCallbackQuery()) {
            callbackEditMessage(update);
                // TODO: Реализовать обработку нажатий на inline-кнопки
                // Пример логики:
                // 1. Получить данные из callbackQuery (callbackData)
                // 2. Определить действие по callbackData
                // 3. Обновить сообщение или выполнить команду
                // 4. Отправить подтверждение о получении callback
                // Обработка нажатий на inline-кнопки
            } else if (update.hasMessage()) {
                // Обработка обычных сообщений
                handleMessage(update.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error processing update: " + e.getMessage());
        }
//        Если update содержит ТЕКСТ и ЯВЛЯЕТСЯ текстом.
//        if (update.hasMessage() && update.getMessage().hasText()) {
//
//            // Запоминаем ID и TEXT.
//            Long chatId = update.getMessage().getChatId();
//            String message_text = update.getMessage().getText();
//            System.out.println(update.getMessage().getText() + " " + chatId);
//
//            // Проверю есть ли в тексте "/", если да - передаю в HandleCommand для соответствующего ответа.
//            if (message_text.startsWith("/")) {
//                new HandleCommand().handleCommand(chatId, message_text);
//            } else {
//                // Эхо ответ.
//                new SenderMessage().sendTextMessage(chatId, message_text);
//            }
//
//            // Иначе проверяю на наличие фотографий.
//        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
//            System.out.println("Пришло фото.");
//            Long chatId = update.getMessage().getChatId();
//            // Кладём в коллекцию фото разных размеров.
//            List<PhotoSize> photoSizes = update.getMessage().getPhoto();
//            // Присваиваем ID фотографии.
//            String f_id = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize))
//                    .map(PhotoSize::getFileId)
//                    .orElse("");
//            // Присваиваем разрешение фотографии. Высота.
//            int f_width = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize))
//                    .map(PhotoSize::getWidth)
//                    .orElse(0);
//            // Присваиваем разрешение фотографии. Ширина.
//            int f_height = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize))
//                    .map(PhotoSize::getHeight)
//                    .orElse(0);
//            // Присваиваем к описанию фотографии вышеуказанный текст.
//            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
//            // Создаю объект InputFile с ID фотографии для отправки.
//            InputFile inputFile = new InputFile(f_id);
//            // Создаю объект класса для вызова метода и передачи параметров для отправки.
//            new SenderMessage().sendPhotoMessage(chatId, inputFile, caption);
//
//        }
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


    // Метод для обработки сообщений разных типов
    private void handleMessage(Message message) {
        // Получаем идентификатор чата для ответа
        Long chatId = message.getChatId();

        // Второй уровень проверки: тип содержимого.
        if (message.hasText()) {
            // Текстовые сообщения
            handleTextMessage(chatId, message);
        } else if (message.hasPhoto()) {
            // Сообщения с фотографиями
            //TODO
        } else if (message.hasDocument()) {
            // Сообщения с документами
            //TODO
        } else {
            // Все остальные сообщения.
            //TODO
        }
    }

    // Обработка текстовых сообщений
    private void handleTextMessage(Long chatId, Message message) {
        String text = message.getText();

        // Если текс начинается с "/" - это команда
        if (text.startsWith("/")) {
            commandHandler.handleCommand(chatId, text);
        } else {
            // Иначе эхо ответ
            sender.sendTextMessage(chatId, text);
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
        new SenderMessage(telegramClient).sendTextMessage(chatId, "Handled callback: " + callbackData);
    }
}
