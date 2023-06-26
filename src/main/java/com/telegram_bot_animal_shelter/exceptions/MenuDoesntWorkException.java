package com.telegram_bot_animal_shelter.exceptions;
/**
 * Class DogNotFoundException
 * @author Kucherenko V.V
 * @version 1.0.0
 */
public class MenuDoesntWorkException extends RuntimeException{

    public MenuDoesntWorkException(String message) {
        super("Ошибка при вызове меню");
    }
}
