package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.PersonCat;

import java.util.Collection;

/**
 * Interface PersonCatServiceImpl
 * @author
 * @version 1.0.0
 */
public interface PersonCatService {

    PersonCat getByIdPersonCat(Long id);

    PersonCat addPersonCat(PersonCat personCat);

    PersonCat updatePersonCat(PersonCat personCat);

    Collection<PersonCat> getAllPersonCat();

    void removeByIdPersonCat(Long id);
}
