package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface DogRepository
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
