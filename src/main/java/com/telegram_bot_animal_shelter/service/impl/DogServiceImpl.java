package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.exceptions.DogNotFoundException;
import com.telegram_bot_animal_shelter.model.Dog;
import com.telegram_bot_animal_shelter.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Class DogService
 * @author
 * @version 1.0.0
 */
@Service
public class DogServiceImpl implements com.telegram_bot_animal_shelter.service.DogService {

    private final DogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(DogServiceImpl.class);

    public DogServiceImpl(DogRepository dogRepository) {
        this.repository = dogRepository;
    }

    /**
     * Method for getting dog by id
     * @param id
     * @return
     */
    @Override
    public Dog getByIdDog(Long id) {
        logger.info("Was invoked method to get a dog by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(DogNotFoundException::new);
    }

    /**
     * Method for adding dog
     * @param dog
     * @return
     */
    @Override
    public Dog addDog(Dog dog) {
        logger.info("Was invoked method to add a dog");
        return this.repository.save(dog);
    }

    /**
     * Method for updates dog
     * @param dog
     * @return
     */
    @Override
    public Dog updateDog(Dog dog) {
        logger.info("Was invoked method to update a dog");
        if (dog.getId() != null) {
            if (getByIdDog(dog.getId()) != null) {
                return this.repository.save(dog);
            }
        }
            throw new DogNotFoundException();
    }

    /**
     * Method for getting all dog
     * @return
     */
    @Override
    public Collection<Dog> getAllDog() {
        logger.info("Was invoked method to get all dogs");
        return this.repository.findAll();
    }

    /**
     * Method for remove dog by id
     * @param id
     */
    @Override
    public void removeByIdDog(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);
        this.repository.deleteById(id);
    }
}
