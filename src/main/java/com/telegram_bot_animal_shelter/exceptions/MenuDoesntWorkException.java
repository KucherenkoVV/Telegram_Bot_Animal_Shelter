package com.telegram_bot_animal_shelter.exceptions;

public class MenuDoesntWorkException extends RuntimeException{

    public MenuDoesntWorkException(String message) {
        super("Ошибка при вызове меню");
    }
}
