package com.telegram_bot_animal_shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class ReportNotFoundException
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException() {
        super("Отчёт не найден!");
    }
}
