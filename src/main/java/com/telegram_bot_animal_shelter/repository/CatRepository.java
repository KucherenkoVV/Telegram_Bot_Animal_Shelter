package com.telegram_bot_animal_shelter.repository;


import com.telegram_bot_animal_shelter.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface CatRepository
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
