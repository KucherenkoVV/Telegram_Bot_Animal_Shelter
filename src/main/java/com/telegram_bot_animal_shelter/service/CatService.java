package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.exceptions.CatNotFoundException;
import com.telegram_bot_animal_shelter.model.Cat;
import com.telegram_bot_animal_shelter.repository.CatRepository;
import com.telegram_bot_animal_shelter.service.impl.CatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CatService implements CatServiceImpl {

    private final CatRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public CatService(CatRepository repository) {
        this.repository = repository;
    }
    @Override
    public Cat getByIdCat(Long id) {
        logger.info("Was invoked method to get a cat by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(CatNotFoundException::new);
    }

    @Override
    public Cat addCat(Cat cat) {
        logger.info("Was invoked method to add a cat");
        return this.repository.save(cat);
    }

    @Override
    public Cat updateCat(Cat cat) {
        logger.info("Was invoked method to update a cat");
        if (cat.getId() != null) {
            return this.repository.save(cat);
        }
        throw new CatNotFoundException();
    }

    @Override
    public Collection<Cat> getAllCat() {
        logger.info("Was invoked method to get all cats");
        return this.repository.findAll();
    }

    @Override
    public void removeByIdCat(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);
        this.repository.deleteById(id);
    }
}
