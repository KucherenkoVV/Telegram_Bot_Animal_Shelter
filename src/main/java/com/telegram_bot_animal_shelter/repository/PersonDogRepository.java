package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.PersonDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Интерфейс PersonDogRepository
 * @author Zhitar Vladislav
 * @version 1.0.0
 */
@Repository
public interface PersonDogRepository extends JpaRepository<PersonDog, Long> {

    Set<PersonDog> findByChatId(Long chatId);

}