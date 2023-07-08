package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    public Volunteer findByChatId(Long chatId);
}
