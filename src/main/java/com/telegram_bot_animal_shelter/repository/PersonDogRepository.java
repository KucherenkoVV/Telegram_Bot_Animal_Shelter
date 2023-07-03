package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.PersonDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Interface PersonDogRepository
 * @author
 * @version 1.0.0
 */
@Repository
public interface PersonDogRepository extends JpaRepository<PersonDog, Long> {

    public PersonDog findByChatId(Long chatId);

    @Transactional
    @Modifying
    @Query("UPDATE PersonDog p set p.name = :name, p.phone = :phone where p.chatId = :chatId")
    public void updatePersonDog(@Param("name") String name,
                            @Param("phone") String phone,
                            @Param("chatId") Long chatId);
}