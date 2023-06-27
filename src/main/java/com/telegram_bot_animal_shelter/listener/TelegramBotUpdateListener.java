package com.telegram_bot_animal_shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.telegram_bot_animal_shelter.exceptions.MenuDoesntWorkException;
import com.telegram_bot_animal_shelter.keyboard.KeyBoardShelter;
import com.telegram_bot_animal_shelter.model.PersonCat;
import com.telegram_bot_animal_shelter.model.PersonDog;
import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.repository.PersonCatRepository;
import com.telegram_bot_animal_shelter.repository.PersonDogRepository;
import com.telegram_bot_animal_shelter.repository.ReportRepository;
import com.telegram_bot_animal_shelter.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;

/**
 * @author Kucherenko V.V
 * @version 0.0.1
 */

@Component
public class TelegramBotUpdateListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    private ReportRepository reportRepository;
    private PersonDogRepository personDogRepository;
    private PersonCatRepository personCatRepository;
    private KeyBoardShelter keyBoardShelter;
    private ReportService reportService;
    private com.pengrad.telegrambot.TelegramBot telegramBot;
    private Report report;
    private PersonCat personCat;
    private PersonDog personDog;
    private final Pattern pattern = Pattern.compile(REGEX_MESSAGE);

    public TelegramBotUpdateListener(ReportRepository reportRepository, PersonDogRepository personDogRepository,
                                     PersonCatRepository personCatRepository, KeyBoardShelter keyBoardShelter,
                                     ReportService reportService, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.personDogRepository = personDogRepository;
        this.personCatRepository = personCatRepository;
        this.keyBoardShelter = keyBoardShelter;
        this.reportService = reportService;
        this.telegramBot = telegramBot;

    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    /**
     * A method that allows you to track and organize the entire process of communication with the user.
     * Choose menu from switch-case after phrase from keyboard button.
     *
     * @param updates
     */
    @Override
    public int process(List<Update> updates) {

        report = new Report();

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            String nameUser = update.message().chat().firstName();
            String textUpdate = update.message().text();
            Integer messageId = update.message().messageId();
            Long chatId = update.message().chat().id();
            Calendar calendar = new GregorianCalendar();

            checkReportDays(update, chatId, calendar);

            try {
                if (update.message() != null && update.message().contact() != null) {
                    shareContact(update);
                }

                if (textUpdate != null) {
                    switch (textUpdate) {

                        case START:
                            sendMessage(chatId, nameUser + HI);
                            keyBoardShelter.chooseMenu(chatId);
                            break;

                        case CAT:
                            keyBoardShelter.sendMenu(chatId);
                            sendMessage(chatId, SET_CAT_ANIMAL);
                            setNewPersonCat();
                            break;

                        case DOG:
                            keyBoardShelter.sendMenu(chatId);
                            sendMessage(chatId, SET_DOG_ANIMAL);
                            setNewPersonDog();
                            break;

                        case MAIN_MENU:
                        case RETURN_MENU:
                            keyBoardShelter.sendMenu(chatId);
                            break;

                        case RETURN_TO_SHELTER_CHOOSE_MENU:
                            keyBoardShelter.chooseMenu(chatId);
                            break;

                        case DRIVER_SCHEME:
                            sendMessage(chatId, SCHEMA_2GIS);
                            break;

                        case FOR_SAFETY:
                            sendMessage(chatId, SAFETY);
                            break;


                        case SHELTER_INFO_MENU:
                            keyBoardShelter.sendMenuInfoShelter(chatId);
                            break;

                        case ABOUT_ANIMAL_SHELTER:
                            if (personCat.isChooseCat()) {
                                sendMessage(chatId, INFO_ABOUT_SHELTER_CAT);
                            } else if (personDog.isChooseDog()) {
                                sendMessage(chatId, INFO_ABOUT_SHELTER_DOG);
                            }
                            break;

                        case TIPS_AND_RECOMMENDATIONS:
                            if (personCat.isChooseCat()) {
                                sendMessage(chatId, INFO_ABOUT_CATS);
                            } else if (personDog.isChooseDog()) {
                                sendMessage(chatId, INFO_ABOUT_DOGS);
                            }
                            break;

                        case DOCUMENTS:
                            sendMessage(chatId, INFO_ABOUT_DOCUMENTS);
                            break;

                        case GET_ANIMAL_WITH_DEFECTS:
                            sendMessage(chatId, INFO_ABOUT_ANIMAL_WITH_DEFECTS);
                            break;

                        case SEND_REPORT:
                            sendMessage(chatId, INFO_ABOUT_REPORT);
                            sendMessage(chatId, REPORT_EXAMPLE);
                            break;

                        case HOW_GET_ANIMAL:
                            keyBoardShelter.sendMenuTakeAnimal(chatId);
                            break;

                        case INFO_ABOUT_BOT_BUTTON:
                            sendMessage(chatId, INFO_ABOUT_BOT);
                            break;

                        case GET_USER_CONTACT:
                            new ReplyKeyboardMarkup(new KeyboardButton(" ")
                                    .requestContact(true));
                            shareContact(update);
                            break;

                        case SEND_MESSAGE_VOLUNTEER:
                            try {
                                URL url = new URL(VOLUNTEER_URL);
                                sendMessage(chatId, VOLUNTEER_QUESTION + url);
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        case EMPTY:
                            sendMessage(chatId, EMPTY_MESSAGE);
                            break;

                        case SAY_HI:
                        case SAY_HI2:
                            sendMessage(chatId, "И тебе привет, " + nameUser + ". Возьми питомца из приюта.");

                        default:
                            sendReplyMessage(chatId, UNKNOWN_MESSAGE, messageId);
                            break;

                    }
                }
            } catch (MenuDoesntWorkException e) {
                e.printStackTrace();
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void setNewPersonDog() {
        personDog = new PersonDog();
        personDog.setChooseDog(true);
        if(personCat != null){
            personCat.setChooseCat(false);
        }
    }

    private void setNewPersonCat() {
        personCat = new PersonCat();
        personCat.setChooseCat(true);
        if(personDog != null){
            personDog.setChooseDog(false);
        }
    }

    private void checkReportDays(Update update, long chatId, Calendar calendar) {
        long compareTime = calendar.get(Calendar.DAY_OF_MONTH);
        report.setDays(reportRepository.findAll().stream()
                .filter(s -> Objects.equals(s.getChatId(), chatId))
                .count() + 1);

        Long lastMessageTime = reportRepository.findAll().stream()
                .filter(s -> s.getChatId() == chatId)
                .map(Report::getLastMessageMs)
                .max(Long::compare)
                .orElseGet(() -> null);
        if (lastMessageTime != null) {
            Date lastDateSendMessage = new Date(lastMessageTime * 1000);
            long numberOfDay = lastDateSendMessage.getDate();

            if (report.getDays() < 30) {
                if (compareTime != numberOfDay) {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        getReport(update);
                    }
                } else {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        sendMessage(chatId, ALREADY_SEND_REPORT);
                    }
                }
                if (report.getDays() >= 30) {
                    sendMessage(chatId, TRIAL_PERIOD_PASSED);
                }
            }
        } else {
            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                getReport(update);
            }
        }
        if (update.message() != null && update.message().photo() != null && update.message().caption() == null) {
            sendMessage(chatId, INCORRECT_REPORT);
        }

    }

    /**
     * A method for sending a reply message.
     *
     * @param chatId
     * @param messageText
     * @param messageId
     */
    public void sendReplyMessage(Long chatId, String messageText, Integer messageId) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyToMessageId(messageId);
        telegramBot.execute(sendMessage);
    }

    /**
     * A method for sending a forwarding message.
     *
     * @param chatId
     * @param messageId
     */
    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(telegramChatVolunteers, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }

    /**
     * A method for sending messages.
     *
     * @param chatId
     * @param text
     */
    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    /**
     * A method for sharing a contact.
     * What doing: Get contact from Users in TelegramBot and write information in local variables and DB
     *
     * @param update
     */
    public void shareContact(Update update) {
        if (update.message().contact() != null) {
            String firstName = update.message().contact().firstName();
            String lastName = update.message().contact().lastName();
            String phone = update.message().contact().phoneNumber();
            String username = update.message().chat().username();
            long finalChatId = update.message().chat().id();
            var sortChatId = personDogRepository.findAll().stream()
                    .filter(i -> i.getChatId() == finalChatId).toList();
            var sortChatIdCat = personCatRepository.findAll().stream()
                    .filter(i -> i.getChatId() == finalChatId).toList();

            if (!sortChatId.isEmpty() || !sortChatIdCat.isEmpty()) {
                sendMessage(finalChatId, ALREADY_IN_DB);
                return;
            }
            if (lastName != null) {
                String name = firstName + " " + lastName + " " + username;
                if (personCat.isChooseCat()) {
                    personCatRepository.save(new PersonCat(name, phone, finalChatId));
                } else if (personDog.isChooseDog()) {
                    personDogRepository.save(new PersonDog(name, phone, finalChatId));
                }
                sendMessage(finalChatId, ADD_TO_DB);
                return;
            }
            if (personCat.isChooseCat()) {
                personCatRepository.save(new PersonCat(firstName, phone, finalChatId));
            } else if (personDog.isChooseDog()) {
                personDogRepository.save(new PersonDog(firstName, phone, finalChatId));
            }
            sendMessage(finalChatId, ADD_TO_DB);

            sendMessage(telegramChatVolunteers, phone + " " + firstName + USER_ADDED_PHONE_NUMBER_TO_DB);
            sendForwardMessage(finalChatId, update.message().messageId());
        }
    }

    /**
     * A method that allows you to receive reports.
     * What doing: send information about received a report by bot
     *
     * @param update
     */
    public void getReport(Update update) {
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String ration = matcher.group(3);
            String health = matcher.group(7);
            String habits = matcher.group(11);

            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file,
                        ration, health, habits, fullPathPhoto, dateSendMessage, timeDate, report.getDays());

                telegramBot.execute(new SendMessage(update.message().chat().id(), REPORT_IS_OK));

                logger.info(REPORT_RECEIVED + update.message().chat().id());
            } catch (IOException e) {
                System.out.println(UPLOAD_PHOTO_ERROR);
            }
        } else {
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file, update.message().caption(),
                        fullPathPhoto, dateSendMessage, timeDate, report.getDays());

                telegramBot.execute(new SendMessage(update.message().chat().id(), REPORT_IS_OK));
                logger.info(REPORT_RECEIVED + update.message().chat().id());
            } catch (IOException e) {
                System.out.println(UPLOAD_PHOTO_ERROR);
            }
        }
    }

    /**
     * A method that allows you to track the sending of reports.
     * What doing: check how many days user was sent correct report
     *
     * @see TelegramBotUpdateListener
     */
    @Scheduled(cron = "* 30 21 * * *")
    public void checkResults() {
        if (report.getDays() < 30) {
            var twoDay = 172800000;
            var nowTime = new Date().getTime() - twoDay;
            var getDistinct = this.reportRepository.findAll().stream()
                    .sorted(Comparator
                            .comparing(Report::getChatId))
                    .max(Comparator
                            .comparing(Report::getLastMessageMs));
            getDistinct.stream()
                    .filter(i -> i.getLastMessageMs() * 1000 < nowTime)
                    .forEach(s -> sendMessage(s.getChatId(), REPORT_NOTIFICATION));
        }
    }
}
