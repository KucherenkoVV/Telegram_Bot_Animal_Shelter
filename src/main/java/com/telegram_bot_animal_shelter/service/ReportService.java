package com.telegram_bot_animal_shelter.service;

import com.pengrad.telegrambot.model.File;
import com.telegram_bot_animal_shelter.exceptions.ReportNotFoundException;
import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.repository.ReportRepository;
import com.telegram_bot_animal_shelter.service.impl.ReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.Date;

/**
 * Class ReportService
 * @author
 * @version 1.0.0
 */
@Service
public class ReportService implements ReportServiceImpl {

    private final ReportRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    /**
     * Method to upload reports
     * @param personId
     * @param pictureFile
     * @param file
     * @param ration
     * @param health
     * @param habits
     * @param filePath
     * @param dateSendMessage
     * @param timeDate
     * @param daysOfReports
     */
    @Override
    public void uploadReport(Long personId, byte[] pictureFile, File file, String ration, String health, String habits,
                             String filePath, Date dateSendMessage, Long timeDate, long daysOfReports) {
        logger.info("Was invoked method to uploadReportData");
        Report report = new Report();

        report.setLastMessage(dateSendMessage);
        report.setDays(daysOfReports);
        report.setFilePath(filePath);
        report.setFileSize(file.fileSize());
        report.setLastMessageMs(timeDate);
        report.setChatId(personId);
        report.setData(pictureFile);
        report.setRation(ration);
        report.setHealth(health);
        report.setHabits(habits);
        this.repository.save(report);
    }

    /**
     * Method for getting reports by id
     * @param id
     * @return
     */
    @Override
    public Report getByIdReport(Long id) {
        logger.info("Was invoked method to get a report by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(ReportNotFoundException::new);
    }

    /**
     * Method for getting all reports
     * @return
     */
    @Override
    public Collection<Report> getAllReport() {
        logger.info("Was invoked method to get all reports");
        return this.repository.findAll();
    }

    /**
     * Method for adding reports
     * @param report
     * @return
     */
    @Override
    public Report addReport(Report report) {
        logger.info("Was invoked method to add a report");
        return this.repository.save(report);
    }

    /**
     * Method for updates reports
     * @param report
     * @return
     */
    @Override
    public Report updateReport(Report report) {
        logger.info("Was invoked method to update a report");
        if (report.getId() != null) {
            if (getByIdReport(report.getId()) != null) {
                return this.repository.save(report);
            }
        }
        throw new ReportNotFoundException();
    }

    /**
     * Method for remove reports by id
     * @param id
     */
    @Override
    public void removeByIdReport(Long id) {
        logger.info("Was invoked method to remove a report by id={}", id);
        this.repository.deleteById(id);
    }
}
