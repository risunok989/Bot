package org.example;

import org.checkerframework.checker.units.qual.A;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Execute   {
    public Execute(Long chatId) {
        this.chatId = chatId;
        System.out.println(chatId);
    }

    Long chatId ;
    String urlPhoto = "https://th.bing.com/th/id/OIP.gW4mtbdTC6ePkGrwvHidPwHaEK?rs=1&pid=ImgDetMain";
    private final Long ADMIN_ID =  249438024L;




    // Скачиваем изображение по URL в InputStream
    URL url;

    {
        try {
          URL  url = new URL(urlPhoto);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    InputStream photoStream;

    {
        try {
            photoStream = url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Создаем InputFile из InputStream
    InputFile photoInputFile = new InputFile(photoStream, "photo.jpg");

    // Создаем объект SendPhoto
    SendPhoto sendPhoto = SendPhoto.builder()
            .chatId(chatId)
            .photo(photoInputFile)
            .caption("СОСУ ХУЙ")
            .build();


    public Execute() throws IOException {
    }
}
