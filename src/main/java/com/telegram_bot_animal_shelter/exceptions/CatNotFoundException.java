package com.telegram_bot_animal_shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class CatNotFoundException
 * @author
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatNotFoundException extends RuntimeException {

    public CatNotFoundException() {
        super("Кот не найден!");
    }
}

