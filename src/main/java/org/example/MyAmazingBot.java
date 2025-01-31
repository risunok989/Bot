package org.example;

import org.example.Handlers.HandleCallback;
import org.example.Handlers.HandleMessage;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static java.lang.Math.toIntExact;

//---------------------------------------------------------------------------//
//    Класс для обработки update пользователя.                               //
//---------------------------------------------------------------------------//

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
    private final Long ADMIN_ID = 249438024L;
    private final Long ALEX_ID = 6119250690L;
    private final Long OLGA_ID = 645409728L;
    private final TelegramClient telegramClient;

    // Обработчик команд (паттерн "Стратегия" для разделения логики)

    // Конструктор класса
    public MyAmazingBot(TelegramClient telegramClient) {
//        this.sender = sender;
        // Инициализация Telegram-клиента с токеном из config.properties
//        this.telegramClient = new OkHttpTelegramClient(new GetToken().token());
//        this.commandHandler = new HandleCommand(telegramClient, new SenderUserMessage(telegramClient));
        this.telegramClient = telegramClient;
    }

    //Переопределил главный метод в который приходят все обновления, т.к класс имплеминитрует другой класс.
    @Override
    public void consume(Update update) {
        try {
            // Первый уровень проверки.
            if (update.hasCallbackQuery()) {
                HandleCallback.handle(update,telegramClient);
                // TODO: Реализовать обработку нажатий на inline-кнопки
                // Пример логики:
                // 1. Получить данные из callbackQuery (callbackData)
                // 2. Определить действие по callbackData
                // 3. Обновить сообщение или выполнить команду
                // 4. Отправить подтверждение о получении callback
                // Обработка нажатий на inline-кнопки
            } else if (update.hasMessage()) {
                // Обработка обычных сообщений (Message).
                // Получаем Message (Все данные).
                Message message = update.getMessage();
                new HandleMessage(telegramClient).handleMessage(message);
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
