package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.exceptions.PersonCatNotFoundException;
import com.telegram_bot_animal_shelter.model.PersonCat;
import com.telegram_bot_animal_shelter.repository.PersonCatRepository;
import com.telegram_bot_animal_shelter.service.PersonCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Class PersonCatService
 * @author
 * @version 1.0.0
 */
@Service
public class PersonCatServiceImpl implements PersonCatService {

    private final PersonCatRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(PersonCatServiceImpl.class);

    public PersonCatServiceImpl(PersonCatRepository personCatRepository) {
        this.repository = personCatRepository;
    }

    /**
     * Method for getting person cat by id
     * @param id
     * @return
     */
    @Override
    public PersonCat getByIdPersonCat(Long id) {
        logger.info("Was invoked method to get a personCat by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(PersonCatNotFoundException::new);
    }

    /**
     * Method for adding person cat
     * @param personCat
     * @return
     */
    @Override
    public PersonCat addPersonCat(PersonCat personCat) {
        logger.info("Was invoked method to add a personCat");
        return this.repository.save(personCat);
    }

    /**
     * Method for updates person cat
     * @param personCat
     * @return
     */
    @Override
    public PersonCat updatePersonCat(PersonCat personCat) {
        logger.info("Was invoked method to update a personCat");
        if (personCat.getId() != null) {
            if (getByIdPersonCat(personCat.getId()) != null) {
                return repository.save(personCat);
            }
        }
        throw new PersonCatNotFoundException();
    }

    /**
     * Method for getting all person cat
     * @return
     */
    @Override
    public Collection<PersonCat> getAllPersonCat() {
        logger.info("Was invoked method to get all personsCat");
        return this.repository.findAll();
    }

    /**
     * Method for remove person cat by id
     * @param id
     */
    @Override
    public void removeByIdPersonCat(Long id) {
        logger.info("Was invoked method to remove a personCat by id={}", id);
        this.repository.deleteById(id);
    }
}
