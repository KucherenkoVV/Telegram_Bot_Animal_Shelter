package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.model.PersonCat;

import java.util.Collection;

public interface PersonCatServiceImpl {

    PersonCat getByIdPersonCat(Long id);

    PersonCat addPersonCat(PersonCat personCat);

    PersonCat updatePersonCat(PersonCat personCat);

    Collection<PersonCat> getAllPersonCat();

    void removeByIdPersonCat(Long id);
}
