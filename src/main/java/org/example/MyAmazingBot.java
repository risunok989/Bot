package org.example;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {

    //Переобределил метод, т.к клас имплеминитрует зависимость.
    @Override
    public void consume(Update update) {
        //Если update содержит ТЕКСТ и ЯВЛЯЕТСЯ текстом.
    if (update.hasMessage() && update.getMessage().hasText()) {
        System.out.println(update.getMessage().getText());
    }
    }
}
