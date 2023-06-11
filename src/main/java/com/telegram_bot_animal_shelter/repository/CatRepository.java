package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}