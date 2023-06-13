package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.PersonCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Интерфейс PersonCatRepository
 * @author Zhitar Vladislav
 * @version 1.0.0
 */
@Repository
public interface PersonCatRepository extends JpaRepository<PersonCat, Long> {

    Set<PersonCat> findByChatId(Long chatId);

}
