package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

public class InlineKeyboard {

    //---------------------------------------------------------------------------//
    //    Класс для создания клавиатуры.                                         //
    //---------------------------------------------------------------------------//

    public InlineKeyboardMarkup inlineKeyboardMarkup() {
        // 1. Создаём кнопку.
        InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton
                .builder()
                .text("Wildberries")
                .callbackData("Wildberries_update")
                .build();
        // 1.1 Создаём вторую кнопку.
        InlineKeyboardButton inlineKeyboardButton2 = InlineKeyboardButton
                .builder()
                .text("Ozon")
                .callbackData("Ozon_update")
                .build();
        // 2. Создаём строку с кнопкой
        // Присваиваем кнопки.
        InlineKeyboardRow row = new InlineKeyboardRow(inlineKeyboardButton, inlineKeyboardButton2);
        // 3. Собираем клавиатуру
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();
        return inlineKeyboardMarkup;
    }


}
