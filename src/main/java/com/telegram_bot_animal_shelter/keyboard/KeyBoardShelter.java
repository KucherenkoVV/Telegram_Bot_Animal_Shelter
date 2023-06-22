package com.telegram_bot_animal_shelter.keyboard;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.telegram_bot_animal_shelter.listener.TelegramBotUpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyBoardShelter {


    @Autowired
    private TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

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
                new KeyboardButton("Информация о возможностях бота"),
                new KeyboardButton("Информация о приюте"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять питомца из приюта"),
                new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Обратиться к волонтеру"));

        keyboardUpdate(replyKeyboardMarkup);

        SendMessage request = new SendMessage(chatId, "Добро пожаловать, в наш приют")
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

        ReplyKeyboardMarkup replyKeyboardMarkup2 = new ReplyKeyboardMarkup(new KeyboardButton("О приюте"),
                new KeyboardButton("Оставить контактные данные").requestContact(true))
                .addRow(new KeyboardButton("Схема проезда, пропуск"), new KeyboardButton("Техника безопасности"));
        repeatableMenu(chatId, replyKeyboardMarkup2);
    }

    /**
     * Repeatable menu about volunteer and shelter.
     * @param chatId
     * @param replyKeyboardMarkup2
     */
    private void repeatableMenu(long chatId, ReplyKeyboardMarkup replyKeyboardMarkup2) {
        replyKeyboardMarkup2.addRow(new KeyboardButton("Обратиться к волонтеру"),
                new KeyboardButton("Вернуться в меню"))
                .addRow(new KeyboardButton("Вернуться к выбору приюта"));

        keyboardUpdate(replyKeyboardMarkup2);

        SendMessage request = new SendMessage(chatId, "Здесь вы сможете найти всю необходимую информацию.")
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

        ReplyKeyboardMarkup replyKeyboardMarkup3 = new ReplyKeyboardMarkup(new KeyboardButton("Советы и рекомендации"),
                new KeyboardButton("Оставить контактные данные"))
                .addRow(new KeyboardButton("Необходимые документы"), new KeyboardButton("Взять питомца с ограниченными возможностями"));
        repeatableMenu(chatId, replyKeyboardMarkup3);
    }

    public void chooseMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано меню выбора ");

        String cat = "Кошка";
        String dog = "Собака";
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(cat));
        replyKeyboardMarkup.addRow(new KeyboardButton(dog));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Выберите, кого хотите приютить:");
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
