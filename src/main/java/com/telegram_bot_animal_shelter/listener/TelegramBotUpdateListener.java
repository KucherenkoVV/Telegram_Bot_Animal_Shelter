package com.telegram_bot_animal_shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.telegram_bot_animal_shelter.keyboard.KeyBoardShelter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhitar Vladislav
 * @version 1.0.0
 */

@Component
public class TelegramBotUpdateListener implements UpdatesListener {

    private static final String START = "/start";

    private static final String HI_TEXT = "Привет, меня зовут Макс, я помогу тебе с усыновлением собаки или кошки, " +
            "для этого выбери нужную кнопку!";

    private static final String CAT = "Кошачий приют";

    private static final String DOG = "Собачий приют";

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    @Autowired
    private KeyBoardShelter keyBoardShelter;
    private final TelegramBot telegramBot;

    public TelegramBotUpdateListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        try {
            list.stream()
                    .filter(update -> update.message() != null)
                    .forEach(update -> {
                        logger.info("Handles update: {}", update);
                        Message message = update.message();
                        Long chatId = message.chat().id();
                        String text = message.text();
                        if (text != null) {

                            switch (text) {
                                case START -> {
                                    sendMessage(chatId, HI_TEXT);
                                    keyBoardShelter.sendMenu(chatId);
                                }
                                case CAT -> sendMessage(chatId, "Вы выбрали кошачий приют");
                                case DOG -> sendMessage(chatId, "Вы выбрали собачий приют");
                            }
                        }
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
}
