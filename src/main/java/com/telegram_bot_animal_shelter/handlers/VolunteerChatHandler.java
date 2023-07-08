package com.telegram_bot_animal_shelter.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.telegram_bot_animal_shelter.keyboard.KeyBoardShelter;
import com.telegram_bot_animal_shelter.listener.TelegramBotUpdateListener;
import com.telegram_bot_animal_shelter.model.Volunteer;
import com.telegram_bot_animal_shelter.repository.PersonCatRepository;
import com.telegram_bot_animal_shelter.repository.PersonDogRepository;
import com.telegram_bot_animal_shelter.repository.VolunteerRepository;
import com.telegram_bot_animal_shelter.service.VolunteerService;
import com.telegram_bot_animal_shelter.constants.StringConstants.*;

import java.util.List;
import java.util.Objects;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;

public class VolunteerChatHandler {

    private Volunteer volunteer;
    private VolunteerService volunteerService;
    private KeyBoardShelter keyBoardShelter;
    private TelegramBot telegramBot;
    private TelegramBotUpdateListener telegramBotUpdateListener;
    private VolunteerRepository volunteerRepository;
    private PersonCatRepository personCatRepository;
    private PersonDogRepository personDogRepository;

    public VolunteerChatHandler(Volunteer volunteer, VolunteerService volunteerService, KeyBoardShelter keyBoardShelter,
                                TelegramBot telegramBot, TelegramBotUpdateListener telegramBotUpdateListener,
                                VolunteerRepository volunteerRepository, PersonCatRepository personCatRepository,
                                PersonDogRepository personDogRepository) {
        this.volunteer = volunteer;
        this.volunteerService = volunteerService;
        this.keyBoardShelter = keyBoardShelter;
        this.telegramBot = telegramBot;
        this.telegramBotUpdateListener = telegramBotUpdateListener;
        this.volunteerRepository = volunteerRepository;
        this.personCatRepository = personCatRepository;
        this.personDogRepository = personDogRepository;
    }

    public VolunteerChatHandler() {
    }

    public void startChatWithVolunteer(Update update) {
        sendMessage(update.message().chat().id(), START_CHAT_USER);
        sendMessage(VOLUNTEER_CHAT_ID, START_CHAT_VOLUNTEER);
    }

    public void chatWithVolunteer(String textUpdate, Integer messageId, Long chatId) {
        if (textUpdate.contains(SEND)) {
            if (personCatRepository.findByChatId(chatId).getChatId().equals(chatId) ||
                    personDogRepository.findByChatId(chatId).getChatId().equals(chatId)) {
                if (volunteerService.getByChatIdVolunteer(VOLUNTEER_CHAT_ID) == null) {
                    volunteerRepository.save(new Volunteer(VOLUNTEER_CHAT_ID, VOLUNTEER_NAME, chatId));
                }
                textUpdate = textUpdate.replace(SEND, "");
                sendMessage(VOLUNTEER_CHAT_ID, QUESTION + textUpdate);
            }
        } else if (textUpdate.contains(REPLY)) {
            if (volunteerRepository.findByChatId(chatId).getChatId().equals(chatId)) {
                textUpdate = textUpdate.replace(REPLY, "");
                sendMessage(volunteerRepository.findByChatId(chatId).getChatIdUser(), ANSWER + textUpdate);
            }
        } else {
            telegramBotUpdateListener.sendReplyMessage(chatId, UNKNOWN_MESSAGE, messageId);
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }
}

