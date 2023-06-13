package com.telegram_bot_animal_shelter.keyboard;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
<<<<<<< HEAD
=======
//import com.telegram_bot_animal_shelter.listener.TelegramBotUpdateListener;
>>>>>>> origin/1-feature-Vitaly
import com.telegram_bot_animal_shelter.listener.TelegramBotUpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Component;

@Component
public class KeyBoardShelter {

    @Autowired
=======
import org.springframework.stereotype.Service;

@Service
public class KeyBoardShelter {


>>>>>>> origin/1-feature-Vitaly
    private TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

<<<<<<< HEAD
    /** Метод, позволяющий выбрать приют;
     * @param chatId
     */
    public void chooseAnimals(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано меню выбора ");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(new KeyboardButton("Кошачий приют"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Собачий приют"));
       returnsAResponseToTheUser(replyKeyboardMarkup, chatId, "Выберите приют:");
    }

    /** Метод, позволяющий вызват основное меню;
     * @param chatId
     */
    public void mainMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано основное меню ");
=======
    /**
     * Display menu with a basic set of buttons.
     * Class methods used: {@code ReplyKeyboardMarkup}
     * Class methods used: {@code SendMessage}
     * @param chatId
     * @see KeyBoardShelter
     */
    public void sendMenu(long chatId) {
        logger.info("Starting main menu: {}, {}", chatId, "Вызвано основное меню");
>>>>>>> origin/1-feature-Vitaly

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о возможностях бота"),
                new KeyboardButton("Узнать информацию о приюте"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять питомца из приюта"),
                new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"));

<<<<<<< HEAD
        returnsAResponseToTheUser(replyKeyboardMarkup, chatId, "Главное меню");
    }

    /**
     * Метод, позволяющий получить информацию о приюте;
     * @param chatId
     */
    public void getInformationAboutTheShelter(long chatId) {
        logger.info("Method sendMenuInfoShelter has been run: {}, {}", chatId, "Вызвали ~Информация о приюте~");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(new KeyboardButton("Информация о приюте"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"),
                new KeyboardButton("Вернуться в меню"));
        returnsAResponseToTheUser(replyKeyboardMarkup, chatId, "Информация о приюте");
    }

    /**
     * Метод, позволяющий взять питомца из приюта;
     * @param chatId
     */
    public void sendMenuTakeAnimal(long chatId) {
        logger.info("Method sendMenuTakeAnimal has been run: {}, {}", chatId, "вызвали ~Как взять питомца из приюта~");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Советы и рекомендации"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"),
                new KeyboardButton("Вернуться в меню"));
        returnsAResponseToTheUser(replyKeyboardMarkup, chatId, "Как взять питомца из приюта");
    }

    /** Метод, возращает ответ позьзователю;
     * @param replyKeyboardMarkup
     * @param chatId
     * @param text
     */

    public void returnsAResponseToTheUser(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
=======
>>>>>>> origin/1-feature-Vitaly
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);

<<<<<<< HEAD
        SendMessage request = new SendMessage(chatId, text)
=======
        SendMessage request = new SendMessage(chatId, "Добро пожаловать, в наш приют")
>>>>>>> origin/1-feature-Vitaly
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);

        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
<<<<<<< HEAD
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
    }
=======
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

        ReplyKeyboardMarkup replyKeyboardMarkup2 = new ReplyKeyboardMarkup(new KeyboardButton("Статья"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        repeatableMenu(chatId, replyKeyboardMarkup2);
    }

    /**
     *
     * @param chatId
     * @param replyKeyboardMarkup2
     */
    private void repeatableMenu(long chatId, ReplyKeyboardMarkup replyKeyboardMarkup2) {
        replyKeyboardMarkup2.addRow(new KeyboardButton("Позвать волонтера"),
                new KeyboardButton("Вернуться в меню"));

        replyKeyboardMarkup2.resizeKeyboard(true);
        replyKeyboardMarkup2.oneTimeKeyboard(false);
        replyKeyboardMarkup2.selective(false);

        SendMessage request = new SendMessage(chatId, "Информацию о приюте")
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
        logger.info("Starting menu about take animal from shelter: {}, {}", chatId, "вызвали Как взять питомца из приюта");

        ReplyKeyboardMarkup replyKeyboardMarkup3 = new ReplyKeyboardMarkup(new KeyboardButton("Кидает на статью"),
                new KeyboardButton("Оставить контактные данные"));
        repeatableMenu(chatId, replyKeyboardMarkup3);
    }
>>>>>>> origin/1-feature-Vitaly
}
