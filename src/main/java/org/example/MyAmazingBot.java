package org.example;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
    private TelegramClient telegramClient = new OkHttpTelegramClient("5384659247:AAG9CS7x4Dk_HRt6kDkzeuu9PWFRJAbhMDY");

    //Переопределил метод, т.к клас имплеминитрует зависимость.
    @Override
    public void consume(Update update) {
        //Если update содержит ТЕКСТ и ЯВЛЯЕТСЯ текстом.
    if (update.hasMessage() && update.getMessage().hasText()) {
        System.out.println(update.getMessage().getText());
        // Запоминаем ID и TEXT.
        String message_text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        // Создаём объект сообщения с помощью паттерна Builder.
        SendMessage sendMessage = SendMessage
                .builder()  // Создаем Builder
                .chatId(chatId)  // Устанавливаем chatId
                .text("Вы пизданули : " + message_text + " Кстати, товй ID мразь : " + chatId)  // Устанавливаем text
                .build();   // Создаем объект SendMessage

        // Отправляем сообщение, передавая созданный объект раньше.
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
    }
}
