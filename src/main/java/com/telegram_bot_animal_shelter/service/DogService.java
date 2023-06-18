package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.Dog;

import java.util.Collection;

/**
 * Interface DogServiceImpl
 * @author
 * @version 1.0.0
 */
public interface DogService {

    Dog getByIdDog(Long id);

    Dog addDog(Dog dog);

    Dog updateDog(Dog dog);

    Collection<Dog> getAllDog();

    void removeByIdDog(Long id);
}
