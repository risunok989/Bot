package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

//---------------------------------------------------------------------------//
//    Класс для отправки сообщений пользователю.                             //
//---------------------------------------------------------------------------//

public class SenderUserMessage {
    private static final Logger logger = LogManager.getLogger(SenderUserMessage.class);
    private final TelegramClient telegramClient;

    public SenderUserMessage(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }


    public void sendTextMessage(Long chatId, String text) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
//        System.out.println("Эхо ответ пользователю с id: " + chatId + ", text: " + text);

        // Вызываем метод у execute (для отправки), в ранее полученном объекте через конструктор.
        executeMessage(sendMessage);

    }

    public void createPlatformKeyboard(Long chatId, String text) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(new InlineKeyboard().inlineKeyboardMarkup())
                .build();

        // Вызываем метод у execute (для отправки), в ранее полученном объекте через конструктор.
        executeMessage(sendMessage);
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
        Long adminId = new GetToken().adminID();
        SendMessage sendMessage = SendMessage.builder().chatId(adminId).text("Я запущен.").build();
        executeMessage(sendMessage);
    }

    // Отправка подготовленного сообщения Телеграмм Клиенту.
    public void executeMessage(SendMessage message) {
            try {
                logger.info("!!! Сообщение: '{}' отправлено пользователю: '{}'  !!!", message.getText(), message.getChatId());
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                logger.error("Ошибка отправки сообщения: {}ID: {}", message.getText(), message.getChatId());
            }

        }




    public void executeMessage(EditMessageText message) {
        try {
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}

