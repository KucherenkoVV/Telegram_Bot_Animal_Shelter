package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.exceptions.DogNotFoundException;
import com.telegram_bot_animal_shelter.model.Dog;
import com.telegram_bot_animal_shelter.repository.DogRepository;
import com.telegram_bot_animal_shelter.service.impl.DogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DogService implements DogServiceImpl {

    private final DogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public DogService(DogRepository dogRepository) {
        this.repository = dogRepository;
    }

    @Override
    public Dog getByIdDog(Long id) {
        logger.info("Was invoked method to get a dog by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(DogNotFoundException::new);
    }

    @Override
    public Dog addDog(Dog dog) {
        logger.info("Was invoked method to add a dog");
        return this.repository.save(dog);
    }

    @Override
    public Dog updateDog(Dog dog) {
        logger.info("Was invoked method to update a dog");
        if (dog.getId() != null) {
            return this.repository.save(dog);
        }
        throw new DogNotFoundException();
    }

    @Override
    public Collection<Dog> getAllDog() {
        logger.info("Was invoked method to get all dogs");
        return this.repository.findAll();
    }

    @Override
    public void removeByIdDog(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);
        this.repository.deleteById(id);
    }
}
