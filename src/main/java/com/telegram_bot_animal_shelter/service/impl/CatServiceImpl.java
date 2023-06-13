package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.model.Cat;

import java.util.Collection;

public interface CatServiceImpl {
    Cat getByIdCat(Long id);

    Cat addCat(Cat cat);

    Cat updateCat(Cat cat);

    Collection<Cat> getAllCat();

    void removeByIdCat(Long id);
}
