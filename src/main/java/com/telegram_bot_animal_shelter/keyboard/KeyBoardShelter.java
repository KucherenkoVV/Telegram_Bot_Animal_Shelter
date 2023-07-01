package com.telegram_bot_animal_shelter.keyboard;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;

/**
 * @author Kucherenko V.V
 * @version 0.0.1
 */
@Service
public class KeyBoardShelter {

    private final com.pengrad.telegrambot.TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(KeyBoardShelter.class);

    public KeyBoardShelter(com.pengrad.telegrambot.TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Display menu with a basic set of buttons.
     * Class methods used: {@code ReplyKeyboardMarkup}
     * Class methods used: {@code SendMessage}
     * @param chatId
     * @see KeyBoardShelter
     */
    public void sendMenu(long chatId) {
        logger.info("Starting main menu: {}, {}", chatId, "Вызвано основное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(INFO_ABOUT_BOT_BUTTON),
                new KeyboardButton(SHELTER_INFO_MENU));
        replyKeyboardMarkup.addRow(new KeyboardButton(HOW_GET_ANIMAL),
                new KeyboardButton(SEND_REPORT));
        replyKeyboardMarkup.addRow(new KeyboardButton(SEND_MESSAGE_VOLUNTEER));

        keyboardUpdate(replyKeyboardMarkup);

        SendMessage request = new SendMessage(chatId, WELCOME)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);

        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("error code: {}", codeError);
            logger.info("description -: {}", description);
        }
    }

    /**
     * Display menu with information about the shelter.
     * Class methods used: {@code ReplyKeyboardMarkup}
     * Class methods used: {@code SendMessage}
     * @param chatId
     * @see KeyBoardShelter
     */
    public void sendMenuInfoShelter(long chatId) {
        logger.info("Starting menu information about animal shelter: {}, {}", chatId, "Вызвано меню: Информация о приюте");

        ReplyKeyboardMarkup replyKeyboardMarkup2 = new ReplyKeyboardMarkup(new KeyboardButton(ABOUT_ANIMAL_SHELTER),
                new KeyboardButton(GET_USER_CONTACT).requestContact(true))
                .addRow(new KeyboardButton(DRIVER_SCHEME), new KeyboardButton(FOR_SAFETY));
        repeatableMenu(chatId, replyKeyboardMarkup2);
    }

    /**
     * Repeatable menu about volunteer and shelter.
     * @param chatId
     * @param replyKeyboardMarkup2
     */
    private void repeatableMenu(long chatId, ReplyKeyboardMarkup replyKeyboardMarkup2) {
        replyKeyboardMarkup2.addRow(new KeyboardButton(SEND_MESSAGE_VOLUNTEER),
                        new KeyboardButton(RETURN_MENU))
                .addRow(new KeyboardButton(RETURN_TO_SHELTER_CHOOSE_MENU));

        keyboardUpdate(replyKeyboardMarkup2);

        SendMessage request = new SendMessage(chatId, FIND_INFORMATION)
                .replyMarkup(replyKeyboardMarkup2)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);

        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("error code: {}", codeError);
            logger.info("description -: {}", description);
        }
    }

    /**
     * Display a menu with information about how to take a pet.
     * Class methods used: {@code ReplyKeyboardMarkup}
     * Class methods used: {@code SendMessage}
     * @param chatId
     * @see KeyBoardShelter
     */
    public void sendMenuTakeAnimal(long chatId) {
        logger.info("Starting menu about take animal from shelter: {}, {}", chatId, "меню: Как взять питомца из приюта");

        ReplyKeyboardMarkup replyKeyboardMarkup3 = new ReplyKeyboardMarkup(new KeyboardButton(TIPS_AND_RECOMMENDATIONS),
                new KeyboardButton(GET_USER_CONTACT))
                .addRow(new KeyboardButton(DOCUMENTS), new KeyboardButton(GET_ANIMAL_WITH_DEFECTS));
        repeatableMenu(chatId, replyKeyboardMarkup3);
    }

    public void chooseMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано меню выбора ");

        String cat = CAT;
        String dog = DOG;
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(cat));
        replyKeyboardMarkup.addRow(new KeyboardButton(dog));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, CHOOSE_PET);
    }

    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        keyboardUpdate(replyKeyboardMarkup);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);

        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
    }

    private static void keyboardUpdate(ReplyKeyboardMarkup replyKeyboardMarkup) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);
    }
}