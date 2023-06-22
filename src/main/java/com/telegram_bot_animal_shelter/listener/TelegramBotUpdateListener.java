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
import java.util.stream.Collectors;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;

/**
 * @author Zhitar Vladislav
 * @version 1.0.0
 */

@Component
public class TelegramBotUpdateListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    private long daysOfReports;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private PersonDogRepository personDogRepository;
    @Autowired
    private PersonCatRepository personCatRepository;
    @Autowired
    private KeyBoardShelter keyBoardShelter;
    @Autowired
    private ReportService reportService;
    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdateListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private boolean isCat = false;

    private PersonCat personCat;

    private PersonDog personDog;

    /**
     * A method that allows you to track and organize the entire process of communication with the user.
     *
     * @param updates
     * @return
     * @see TelegramBotUpdateListener
     */
    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);


            String nameUser = update.message().chat().firstName();
            String textUpdate = update.message().text();
            Integer messageId = update.message().messageId();
            Long chatId = update.message().chat().id();
            Calendar calendar = new GregorianCalendar();
            daysOfReports = reportRepository.findAll().stream()
                    .filter(s -> Objects.equals(s.getChatId(), chatId))
                    .count() + 1;

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

                        case "Кошка":
                            isCat = true;
                            keyBoardShelter.sendMenu(chatId);
                            sendMessage(chatId, "Вы выбрали кошачий приют.");
                            break;

                        case "Собака":
                            isCat = false;
                            keyBoardShelter.sendMenu(chatId);
                            sendMessage(chatId, "Вы выбрали собачий приют.");
                            break;

                        case "Главное меню":
                        case "Вернуться в меню":
                            keyBoardShelter.sendMenu(chatId);
                            break;

                        case "Вернуться к выбору приюта":
                            keyBoardShelter.chooseMenu(chatId);
                            break;

                        case "Схема проезда, пропуск":
                            sendMessage(chatId, SCHEMA_2GIS);
                            break;

                        case "Техника безопасности":
                            sendMessage(chatId, SAFETY);
                            break;


                        case "Информация о приюте":
                            keyBoardShelter.sendMenuInfoShelter(chatId);
                            break;

                        case "О приюте":
                            if (isCat) {
                                sendMessage(chatId, INFO_ABOUT_SHELTER_CAT);
                            } else {
                                sendMessage(chatId, INFO_ABOUT_SHELTER_DOG);
                            }
                            break;

                        case "Советы и рекомендации":
                            if (isCat) {
                                sendMessage(chatId, INFO_ABOUT_CATS);
                            } else {
                                sendMessage(chatId, INFO_ABOUT_DOGS);
                            }
                            break;

                        case "Необходимые документы":
                            sendMessage(chatId, INFO_ABOUT_DOCUMENTS);
                            break;

                        case "Взять питомца с ограниченными возможностями":
                            sendMessage(chatId, INFO_ABOUT_ANIMAL_WITH_DEFECTS);
                            break;

                        case "Прислать отчет о питомце":
                            sendMessage(chatId, INFO_ABOUT_REPORT);
                            sendMessage(chatId, REPORT_EXAMPLE);
                            getReport(update);

                            //присылает хрень
                        case "Как взять питомца из приюта":
                            keyBoardShelter.sendMenuTakeAnimal(chatId);
                            break;

                        case "Информация о возможностях бота":
                            sendMessage(chatId, INFO_ABOUT_BOT);
                            break;

                        case "Оставить контактные данные":
                            new ReplyKeyboardMarkup(new KeyboardButton(" ")
                                            .requestContact(true));
                                shareContact(update);
                            break;

                        case "Обратиться к волонтеру":
                            try {
                                URL url = new URL("https://t.me/mr_Talks");
                                sendMessage(chatId, "Чтобы задать свой вопрос волонтеру, перейдите по ссылке:  " + url);
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        case "":
                            System.out.println("Измените свой вопрос");
                            sendMessage(chatId, "Пустое сообщение");
                            break;

                        case "Привет":
                        case "привет":
                            sendMessage(chatId, "И тебе привет, " + nameUser + ". Возьми питомца из приюта.");

                        default:
                            sendReplyMessage(chatId, "Я не знаю такой команды, возможно вам стоит обратиться к волонтеру", messageId);
                            break;

                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Ошибка");
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void checkReportDays(Update update, long chatId, Calendar calendar) {
        long compareTime = calendar.get(Calendar.DAY_OF_MONTH);

        Long lastMessageTime = reportRepository.findAll().stream()
                .filter(s -> s.getChatId() == chatId)
                .map(Report::getLastMessageMs)
                .max(Long::compare)
                .orElseGet(() -> null);
        if (lastMessageTime != null) {
            Date lastDateSendMessage = new Date(lastMessageTime * 1000);
            long numberOfDay = lastDateSendMessage.getDate();

            if (daysOfReports < 30) {
                if (compareTime != numberOfDay) {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        getReport(update);
                    }
                } else {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        sendMessage(chatId, "Вы уже отправляли отчет сегодня");
                    }
                }
                if (daysOfReports >= 30) {
                    sendMessage(chatId, "Вы прошли испытательный срок!");
                }
            }
        } else {
            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                getReport(update);
            }
        }
        if (update.message() != null && update.message().photo() != null && update.message().caption() == null) {
            sendMessage(chatId, "Некорректно заполнен отчет.");
        }

    }

    /**
     * A method for sending a reply message.
     *
     * @param chatId
     * @param messageText
     * @param messageId
     * @see TelegramBotUpdateListener
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
     * @see TelegramBotUpdateListener
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
     * @see TelegramBotUpdateListener
     */
    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    /**
     * A method for sharing a contact.
     * What doing: Get contact from Users in TelegramBot and write information in local variables and DB
     * @param update
     * @see TelegramBotUpdateListener
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
                    .filter(i -> i.getChatId() == finalChatId).toList();;

            if (!sortChatId.isEmpty() || !sortChatIdCat.isEmpty()) {
                sendMessage(finalChatId, "Вы уже в базе!");
                return;
            }
            if (lastName != null) {
                String name = firstName + " " + lastName + " " + username;
                if (isCat) {
                    personCat = personCatRepository.save(new PersonCat(name, phone, finalChatId));
                } else {
                    personDog = personDogRepository.save(new PersonDog(name, phone, finalChatId));
                }
                sendMessage(finalChatId, "Вас успешно добавили в базу. Скоро вам перезвонят.");
                return;
            }
            if (isCat) {
                personCat = personCatRepository.save(new PersonCat(firstName, phone, finalChatId));
            } else {
                personDog = personDogRepository.save(new PersonDog(firstName, phone, finalChatId));
            }
            sendMessage(finalChatId, "Вас успешно добавили в базу! Скоро вам перезвонят.");

            sendMessage(telegramChatVolunteers, phone + " " + firstName + " Добавил(а) свой номер в базу");
            sendForwardMessage(finalChatId, update.message().messageId());
        }
    }

    /**
     * A method that allows you to receive reports.
     * What doing: send information about received a report by bot.
     * @param update
     * @see TelegramBotUpdateListener
     */
    public void getReport(Update update) {
        Pattern pattern = Pattern.compile(REGEX_MESSAGE);
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String ration = matcher.group(3);
            String health = matcher.group(7);
            String habits = matcher.group(11);

            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file,
                        ration, health, habits, fullPathPhoto, dateSendMessage, timeDate, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));

                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        } else {
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file, update.message().caption(),
                        fullPathPhoto, dateSendMessage, timeDate, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        }
    }

    /**
     * A method that allows you to track the sending of reports.
     * What doing: check how many days user was sent correct report
     * @see TelegramBotUpdateListener
     */
    @Scheduled(cron = "* 30 21 * * *")
    public void checkResults() {
        if (daysOfReports < 30) {
            var twoDay = 172800000;
            var nowTime = new Date().getTime() - twoDay;
            var getDistinct = this.reportRepository.findAll().stream()
                    .sorted(Comparator
                            .comparing(Report::getChatId))
                    .max(Comparator
                            .comparing(Report::getLastMessageMs));
            getDistinct.stream()
                    .filter(i -> i.getLastMessageMs() * 1000 < nowTime)
                    .forEach(s -> sendMessage(s.getChatId(), "Вы забыли прислать отчет, скорее поторопитесь сделать это!"));
        }
    }
}
