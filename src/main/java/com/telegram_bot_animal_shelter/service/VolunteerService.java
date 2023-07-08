package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.Volunteer;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface VolunteerService {

    Volunteer getByIdVolunteer(Long id);

    Volunteer getByChatIdVolunteer(Long chatId);

    Volunteer addVolunteer(Volunteer volunteer);

    Volunteer updateVolunteer(Volunteer volunteer);

    Collection<Volunteer> getAllVolunteer();

    void removeByIdVolunteer(Long id);
}
