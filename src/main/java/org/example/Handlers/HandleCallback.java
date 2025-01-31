package org.example.Handlers;

import org.example.SenderUserMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static java.lang.Math.toIntExact;

//---------------------------------------------------------------------------//
//                      Класс для обработки Callback от update.              //
//---------------------------------------------------------------------------//

public class HandleCallback {
    public static void handle(Update update, TelegramClient telegramClient) {
        // Извлечение данных, формирование ответа
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
            new SenderUserMessage(telegramClient).executeMessage(new_message);
        } else if (call_data.equals("Ozon_update")) {
            String answer = "Updated message text Ozon";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();
           new SenderUserMessage(telegramClient).executeMessage(new_message);
        }
    }
}
