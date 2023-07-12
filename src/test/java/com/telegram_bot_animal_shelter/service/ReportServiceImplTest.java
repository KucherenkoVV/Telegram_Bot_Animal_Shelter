package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.repository.ReportRepository;
import com.telegram_bot_animal_shelter.service.impl.ReportServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

/**
 * Class ReportServiceImplTest
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
    private static final Long CHAT_ID = 1L;
    private static final String RATION = "CORM";
    private static final String HEALTH = "ПОЛОЖИТЕЛЬНОЕ";
    private static final String HABITS = "ПРИНОСИТЬ ПАЛКУ";
    private static final Long DAYS = 5L;
    private static final String CAPTION = "....";

    private static final List<Report> reports = new ArrayList<>(Arrays.asList(
            new Report(CHAT_ID, RATION, HEALTH, HABITS, DAYS, CAPTION),
            new Report(CHAT_ID, RATION, HEALTH, HABITS, DAYS, CAPTION),
            new Report(CHAT_ID, RATION, HEALTH, HABITS, DAYS, CAPTION)));

    @Mock
    private ReportRepository reportRepositoryMock;

    @InjectMocks
    private ReportServiceImpl reportService;

    private final Report report = new Report(CHAT_ID, RATION, HEALTH, HABITS, DAYS, CAPTION);

    /**
     * Testing method for getById reports
     */
    @Test
    public void getByIdReport() {
        Mockito.when(reportRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(report));
        Report report = reportService.getByIdReport(1L);
        Assertions.assertThat(report.getChatId()).isEqualTo(report.getChatId());
        Assertions.assertThat(report.getRation()).isEqualTo(report.getRation());
        Assertions.assertThat(report.getHealth()).isEqualTo(report.getHealth());
        Assertions.assertThat(report.getHabits()).isEqualTo(report.getHabits());
        Assertions.assertThat(report.getDays()).isEqualTo(report.getDays());
        Assertions.assertThat(report.getCaption()).isEqualTo(report.getCaption());
    }

    /**
     * Testing method for adding reports
     */
    @Test
    public void addReport() {
        Mockito.when(reportRepositoryMock.save(any(Report.class))).thenReturn(report);
        Report report1 = reportService.addReport(report);
        Assertions.assertThat(report1.getChatId()).isEqualTo(report1.getChatId());
        Assertions.assertThat(report1.getRation()).isEqualTo(report1.getRation());
        Assertions.assertThat(report1.getHealth()).isEqualTo(report1.getHealth());
        Assertions.assertThat(report1.getHabits()).isEqualTo(report1.getHabits());
        Assertions.assertThat(report1.getDays()).isEqualTo(report1.getDays());
        Assertions.assertThat(report1.getCaption()).isEqualTo(report1.getCaption());
    }

    /**
     * Testing method for updates reports
     */
    @Test
    public void updateReport() {
        Report report1 = new Report();
        report1.setChatId(1L);
        report1.setRation("МЯсо");
        report1.setHealth("...");
        report1.setHabits("...");
        report1.setDays(1L);
        report1.setCaption("...");
        report1.setId(1L);
        Mockito.when(reportRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(report1));
        Mockito.when(reportRepositoryMock.save(any(Report.class))).thenReturn(report1);
        Report report2 = reportService.updateReport(report1);
        Assertions.assertThat(report2.getChatId()).isEqualTo(report1.getChatId());
        Assertions.assertThat(report2.getRation()).isEqualTo(report1.getRation());
        Assertions.assertThat(report2.getHealth()).isEqualTo(report1.getHealth());
        Assertions.assertThat(report2.getHabits()).isEqualTo(report1.getHabits());
        Assertions.assertThat(report2.getDays()).isEqualTo(report1.getDays());
        Assertions.assertThat(report2.getCaption()).isEqualTo(report1.getCaption());
    }

    /**
     * Testing method for getting all reports
     */
    @Test
    public void getAllReport() {
        Mockito.when(reportRepositoryMock.findAll()).thenReturn(reports);
        Collection<Report> report = reportService.getAllReport();
        Assertions.assertThat(report.size()).isEqualTo(reports.size());
        Assertions.assertThat(report).isEqualTo(reports);
    }


}
