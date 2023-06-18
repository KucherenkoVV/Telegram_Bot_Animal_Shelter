package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.PersonDog;

import java.util.Collection;

/**
 * Interface PersonDogServiceImpl
 * @author
 * @version 1.0.0
 */
public interface PersonDogService {
    PersonDog getByIdPersonDog(Long id);

    PersonDog addPersonDog(PersonDog personDog);

    PersonDog updatePersonDog(PersonDog personDog);

    Collection<PersonDog> getAllPersonDog();

    void removeByIdPersonDog(Long id);
}
