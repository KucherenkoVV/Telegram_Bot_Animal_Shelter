package com.telegram_bot_animal_shelter.service.impl;

import com.telegram_bot_animal_shelter.exceptions.VolunteerNotFoundException;
import com.telegram_bot_animal_shelter.model.Volunteer;
import com.telegram_bot_animal_shelter.repository.VolunteerRepository;
import com.telegram_bot_animal_shelter.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for operation with entity Volunteer
 * @author Kucherenko V.V.
 * @version 0.1.1
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private static final Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Mothod to find Volunteer by her id
     * @param id
     * @return Volunteer or else throw exeption
     */
    @Override
    public Volunteer getByIdVolunteer(Long id) {
        logger.info("Was invoked method to get a Volunteer by id={}", id);
        return this.volunteerRepository.findById(id).orElseThrow(VolunteerNotFoundException::new);
    }

    /**
     * Method to find Volunteer by her chat id from telegram
     * @param chatId
     * @return Volunteer by chatId from telegram
     */
    @Override
    public Volunteer getByChatIdVolunteer(Long chatId) {
        logger.info("Was invoked method to get a Volunteer by chatId={}", chatId);
        return this.volunteerRepository.findByChatId(chatId);
    }

    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        logger.info("Was invoked method to save a new Volunteer");
        return this.volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        logger.info("Was invoked method to update Volunteer");
        if(volunteer.getId() != null && volunteerRepository.findById(volunteer.getId()).isPresent()){
            return this.volunteerRepository.save(volunteer);
        } else {
            throw new VolunteerNotFoundException();
        }
    }

    @Override
    public Collection<Volunteer> getAllVolunteer() {
        logger.info("Was invoked method to get all Volunteers");
        return this.volunteerRepository.findAll();
    }

    @Override
    public void removeByIdVolunteer(Long id) {
        logger.info("Was invoked method to delete Volunteer by id");
        volunteerRepository.deleteById(id);
    }
}
