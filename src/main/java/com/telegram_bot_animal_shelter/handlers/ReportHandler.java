package com.telegram_bot_animal_shelter.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.telegram_bot_animal_shelter.listener.TelegramBotUpdateListener;
import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.repository.ReportRepository;
import com.telegram_bot_animal_shelter.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;
import static com.telegram_bot_animal_shelter.constants.StringConstants.UPLOAD_PHOTO_ERROR;

public class ReportHandler {

    private ReportRepository reportRepository;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    private ReportService reportService;
    private com.pengrad.telegrambot.TelegramBot telegramBot;

    private final Pattern pattern = Pattern.compile(REGEX_MESSAGE);

    public ReportHandler(ReportRepository reportRepository, ReportService reportService, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.reportService = reportService;
        this.telegramBot = telegramBot;
    }

    public ReportHandler() {
    }

    public void checkReportDays(Update update, long chatId, Calendar calendar) {
        long compareTime = calendar.get(Calendar.DAY_OF_MONTH);
        if (reportRepository.findByChatId(chatId) == null) {
            reportRepository.save(new Report(chatId));
        }
        reportRepository.findByChatId(chatId).setDays(reportRepository.findAll().stream()
                .filter(s -> Objects.equals(s.getChatId(), chatId))
                .count() + 1);

        Long lastMessageTime = reportRepository.findByChatId(chatId).getLastMessageMs();
        if (lastMessageTime != null) {
            Date lastDateSendMessage = new Date(lastMessageTime * 1000);
            long numberOfDay = lastDateSendMessage.getDate();

            if (reportRepository.findByChatId(chatId).getDays() < 30) {
                if (compareTime != numberOfDay) {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        getReport(update);
                        checkResults();
                    }
                } else {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        sendMessage(chatId, ALREADY_SEND_REPORT);
                    }
                }
                if (reportRepository.findByChatId(chatId).getDays() >= 30) {
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
     * A method that allows you to receive reports.
     * What doing: send information about received a report by bot
     *
     * @param update
     */
    public void getReport(Update update) {
        Matcher matcher = pattern.matcher(update.message().caption());
        Long reportDays = reportRepository.findByChatId(update.message().chat().id()).getDays();
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
                        ration, health, habits, fullPathPhoto, dateSendMessage, timeDate, reportDays);

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
                        fullPathPhoto, dateSendMessage, timeDate, reportDays);

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
    private void checkResults() {
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

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }
}
