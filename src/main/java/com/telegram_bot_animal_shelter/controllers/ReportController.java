package com.telegram_bot_animal_shelter.controllers;

import com.telegram_bot_animal_shelter.listener.TelegramBotUpdateListener;
import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * Class of ReportDataController.
 * @author Kucherenko.vv
 * @version 0.0.1
 */
@RestController
@RequestMapping("photoReports")
public class ReportController {

    private final ReportServiceImpl reportService;

    private TelegramBotUpdateListener telegramBot;

    private final String fileType = "image/jpeg";

    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @Autowired
    public ReportController(ReportServiceImpl reportService, TelegramBotUpdateListener telegramBot) {
        this.reportService = reportService;
        this.telegramBot = telegramBot;
    }

    @Operation(summary = "Просмотр отчетов по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все отчеты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping("/{id}/report")
    public Report downloadReport(@Parameter(description = "report id") @PathVariable Long id) {
        return this.reportService.getByIdReport(id);
    }

    @Operation(summary = "Удаление отчетов по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @DeleteMapping("/{id}")
    public void remove(@Parameter (description = "report id") @PathVariable Long id) {
        this.reportService.removeByIdReport(id);
    }

    @Operation(summary = "Просмотр всех отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все отчеты, либо отчеты определенного пользователя по chat_id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping("/getAll")
    public ResponseEntity<Collection<Report>> getAll() {
        return ResponseEntity.ok(this.reportService.getAllReport());
    }

    @Operation(summary = "Просмотр фото по id отчета",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    description = "Фото, найденное по id отчета"
            ),
            tags = "Report"
    )
    @GetMapping("/{id}/photo-from-db")
    public ResponseEntity<byte[]> downloadPhotoFromDB(@Parameter (description = "report id") @PathVariable Long id) {
        Report reportData = this.reportService.getByIdReport(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileType));
        headers.setContentLength(reportData.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(reportData.getData());
    }

    @Operation(summary = "Отправить сообщение пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщение определенному пользователю." +
                                    "Например сообщение о том, что необходимо правильно заполнять форму отчета. Либо связаться с волонтерами по номеру",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping("/message-to-person")
    public void sendMessageToPerson(@Parameter(description = "id чата с пользователем", example = "3984892310")
                                    @RequestParam Long chat_Id,
                                    @Parameter(description = "Ваше сообщение")
                                    @RequestParam String message) {
        telegramBot.sendMessage(chat_Id, message);
    }
}

