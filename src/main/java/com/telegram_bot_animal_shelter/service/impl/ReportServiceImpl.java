package com.telegram_bot_animal_shelter.service.impl;

import com.pengrad.telegrambot.model.File;
import com.telegram_bot_animal_shelter.model.Report;

import java.util.Collection;
import java.util.Date;

/**
 * Interface ReportServiceImpl
 * @author
 * @version 1.0.0
 */
public interface ReportServiceImpl {

    void uploadReport(Long personId, byte[] pictureFile, File file, String ration, String health,
                          String habits, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports);

    Report getByIdReport(Long id);

    Collection<Report> getAllReport();

    Report addReport(Report report);

    Report updateReport(Report report);

    void removeByIdReport(Long id);

}
