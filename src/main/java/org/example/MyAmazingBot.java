package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
    TelegramClient telegramClient = new OkHttpTelegramClient(new GetToken().token());
//    SenderMessage senderMessage = new SenderMessage();
    private final Long ADMIN_ID = 249438024L;
    private final Long ALEX_ID = 6119250690L;
    private final Long OLGA_ID = 645409728L;

    //Переопределил метод, т.к клас имплеминитрует зависимость.
    @Override
    public void consume(Update update) {
        //Если update содержит ТЕКСТ и ЯВЛЯЕТСЯ текстом.
        if (update.hasMessage() && update.getMessage().hasText()) {

            // Запоминаем ID и TEXT.
            Long chatId = update.getMessage().getChatId();
            String message_text = update.getMessage().getText();
            System.out.println(update.getMessage().getText() + " " + chatId);

            // Создаём обьект Класса, отправляем обьект с токеном, вызываем нужный метод отправки.
            new SenderMessage().sendTextMessage(chatId, message_text);


//         Создаём объект сообщения с помощью паттерна Builder.
//        SendMessage sendMessage = SendMessage
//                .builder()  // Создаем Builder
//                .chatId(chatId)  // Устанавливаем chatId
//                .text("Вы пизданули : " + message_text + " Кстати, товй ID мразь : " + chatId)  // Устанавливаем text
//                .build();   // Создаем объект SendMessage
//
//
//         //Отправляем сообщение, передавая созданный объект раньше.
//        try {
//            telegramClient.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            System.out.println("Пришло фото.");

            Long chatId = update.getMessage().getChatId();
            // Ложим в коллекцию фото разных размеров.
            List<PhotoSize> photoSizes = update.getMessage().getPhoto();
            // Присваиваем ID фотографии.
            String f_id = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getFileId)
                    .orElse("");
            // Присваиваем разрешение фотографии. Высота.
            int f_width = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getWidth)
                    .orElse(0);
            // Присваиваем разрешение фотографии. Ширина.
            int f_height = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getHeight)
                    .orElse(0);
            // Присваиваем к описанию фотографии вышеуказанный текст.
            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
            // Создаю объект InputFile с ID фотографии для отправки.
            InputFile inputFile = new InputFile(f_id);
            // Создаю объект класса для вызова метода и передачи параметров для отправки.
            new SenderMessage().sendPhotoMessage(chatId,inputFile, caption);

        }
    }
}
