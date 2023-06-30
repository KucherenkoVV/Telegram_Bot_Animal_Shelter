package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.exceptions.PersonDogNotFoundException;
import com.telegram_bot_animal_shelter.model.PersonDog;
import com.telegram_bot_animal_shelter.repository.PersonDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Class PersonDogService
 * @author
 * @version 1.0.0
 */
@Service
public class PersonDogServiceImpl implements com.telegram_bot_animal_shelter.service.PersonDogService {

    private final PersonDogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(PersonDogServiceImpl.class);

    public PersonDogServiceImpl(PersonDogRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for getting person dog by id
     * @param id
     * @return
     */
    @Override
    public PersonDog getByIdPersonDog(Long id) {
        logger.info("Was invoked method to get a personDog by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(PersonDogNotFoundException::new);
    }

    /**
     * Method for adding person dog
     * @param personDog
     * @return
     */
    @Override
    public PersonDog addPersonDog(PersonDog personDog) {
        logger.info("Was invoked method to add a personDog");
        return this.repository.save(personDog);
    }

    /**
     * Method for updates person dog
     * @param personDog
     * @return
     */
    @Override
    public PersonDog updatePersonDog(PersonDog personDog) {
        logger.info("Was invoked method to update a personDog");
        if (personDog.getId() != null) {
            if (getByIdPersonDog(personDog.getId()) != null) {
                return this.repository.save(personDog);
            }
        }
        throw new PersonDogNotFoundException();
    }

    /**
     * Method for getting all person dog
     * @return
     */
    @Override
    public Collection<PersonDog> getAllPersonDog() {
        logger.info("Was invoked method to get all personsDod");
        return this.repository.findAll();
    }

    /**
     * Method for remove person dog by id
     * @param id
     */
    @Override
    public void removeByIdPersonDog(Long id) {
        logger.info("Was invoked method to remove a personDog by id={}", id);
        this.repository.deleteById(id);
    }
}
