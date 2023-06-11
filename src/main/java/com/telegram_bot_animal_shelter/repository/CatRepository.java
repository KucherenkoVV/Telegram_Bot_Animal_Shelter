package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.entity.Cat;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс CatRepository
 * @author Vladislav Zhitar
 * @version 1.0.0
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
