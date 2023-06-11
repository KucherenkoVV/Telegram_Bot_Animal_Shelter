package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.exceptions.CatNotFoundException;
import com.telegram_bot_animal_shelter.model.Cat;
import com.telegram_bot_animal_shelter.repository.CatRepository;
import com.telegram_bot_animal_shelter.service.impl.CatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class of CatService
 * @author Dmitriy Volkov
 * @version 0.0.1
 */
@Service
public class CatService implements CatServiceImpl {

    private final CatRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    @Autowired
    public CatService(CatRepository repository) {
        this.repository = repository;
    }
    @Override
    public Cat getById(Long id) {
        logger.info("Получение кошки по id", id);
        return this.repository.findById(id)
                .orElseThrow(CatNotFoundException::new);
    }
    @Override
    public Cat create(Cat cat) {
        logger.info("Was invoked method to create a cat");

        return this.repository.save(cat);
    }

}

