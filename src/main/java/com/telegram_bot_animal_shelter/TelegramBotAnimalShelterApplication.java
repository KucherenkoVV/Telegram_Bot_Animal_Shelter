package com.telegram_bot_animal_shelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TelegramBotAnimalShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotAnimalShelterApplication.class, args);
    }

}
