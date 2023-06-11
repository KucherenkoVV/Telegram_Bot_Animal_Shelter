package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.model.Cat;

public interface CatServiceImpl {
    public Cat getById(Long id);

    Cat create(Cat cat);
}
