package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SenderMessage {
    private final TelegramClient telegramClient;

    public SenderMessage(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(text).build();

        // Вызываем метод у execute (для отправки), в ранее полученном объекте через конструктор.
        try {
            telegramClient.execute(sendMessage);
            System.err.println("Отправил эхо пользователю с ID : " + sendMessage.getChatId());
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка отправки сообщения", e);
        }
    }

    public void sendPhotoMessage(Long chatId, InputFile photo, String caption) {
        SendPhoto sendPhoto = SendPhoto.builder().chatId(chatId).photo(photo).caption("тест, мать       " + caption).build();
        try {
            try {
                telegramClient.execute(sendPhoto);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Ошибка отправки фотографии", e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendStartMessageForAdmin() {
        SendMessage sendMessage = SendMessage.builder().chatId(249438024L).text("Я запущен.").build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка отправки сообщения ADMIN", e);
        }
    }

}
