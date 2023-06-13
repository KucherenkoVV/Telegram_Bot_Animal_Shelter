package com.telegram_bot_animal_shelter.service.impl;

import com.pengrad.telegrambot.model.File;
import com.telegram_bot_animal_shelter.model.Report;

import java.util.Collection;
import java.util.Date;

public interface ReportServiceImpl {

    void uploadReport(Long personId, byte[] pictureFile, File file, String ration, String health,
                          String habits, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports);

    Report findByIdReport(Long id);

    Collection<Report> getAllReport();

    Report addReport(Report report);

    void removeByIdReport(Long id);

}
