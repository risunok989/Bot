package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetToken {

    public String token() {
        String token;

        // Получаю обьект, для дальнейшего парсинга и работы с настройками.
        Properties prop = new Properties();

        try {
            // Открываем поток для чтения файла
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
            // Загружаем настройки из файла
            try {
                prop.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Файл config.properties не найден в classpath! (Каталоге Main)");
            }
            // Получаю токен из файла
            token = prop.getProperty("bot.token");


            return token;
        } catch (RuntimeException e) {
            throw new RuntimeException(e + "Ошибка чтения config.properties");
        }
    }
    public long adminID() {
        String adminID;

        // Получаю обьект, для дальнейшего парсинга и работы с настройками.
        Properties prop = new Properties();

        try {
            // Открываем поток для чтения файла
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
            // Загружаем настройки из файла
            try {
                prop.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Файл config.properties не найден в classpath! (Каталоге Main)");
            }
            // Получаю токен из файла
            adminID = prop.getProperty("bot.adminID");
            long number = Long.parseLong(adminID);


            return number;
        } catch (RuntimeException e) {
            throw new RuntimeException(e + "Ошибка чтения config.properties");
        }
    }
}
