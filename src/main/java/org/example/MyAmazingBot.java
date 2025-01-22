package org.example;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
     TelegramClient telegramClient = new OkHttpTelegramClient("5384659247:AAG9CS7x4Dk_HRt6kDkzeuu9PWFRJAbhMDY");
    private final Long ADMIN_ID =  249438024L;

    //Переопределил метод, т.к клас имплеминитрует зависимость.
    @Override
    public void consume(Update update) {
        //Если update содержит ТЕКСТ и ЯВЛЯЕТСЯ текстом.
    if (update.hasMessage() && update.getMessage().hasText()) {

        // Запоминаем ID и TEXT.
        String message_text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        System.out.println(update.getMessage().getText() + " " + chatId);


        // Создаём объект сообщения с помощью паттерна Builder.
//        SendMessage sendMessage = SendMessage
//                .builder()  // Создаем Builder
//                .chatId(chatId)  // Устанавливаем chatId
//                .text("Вы пизданули : " + message_text + " Кстати, товй ID мразь : " + chatId)  // Устанавливаем text
//                .build();   // Создаем объект SendMessage


         //Отправляем сообщение, передавая созданный объект раньше.
        try {
            telegramClient.execute(new Execute(chatId).sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
    }
}
