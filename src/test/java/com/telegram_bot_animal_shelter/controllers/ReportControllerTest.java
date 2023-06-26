package com.telegram_bot_animal_shelter.controllers;

import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class ReportControllerTest
 * @author Zhitar Vladislav
 * @version 1.0.0
 */
@WebMvcTest(ReportController.class)
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    /**
     * Testing method getById reports in the controller class
     */
    @Test
    void downloadReport() throws Exception {
       Report report = new Report();
       report.setId(1L);

        when(reportService.getByIdReport(anyLong())).thenReturn(report);

        mockMvc.perform(
                        get("/{id}/report", 1L))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(reportService).getByIdReport(1L);
    }

    /**
     * Testing method deleteById reports in the controller class
     */
    @Test
    void remove() throws Exception {
        mockMvc.perform(
                        delete("/{id}", 1))
                .andExpect(status().isOk());
        verify(reportService).removeByIdReport(1L);
    }

    /**
     * Testing method get all reports in the controller class
     */
    @Test
    void getAll() throws Exception {
        when(reportService.getAllReport()).thenReturn(List.of(new Report()));

        mockMvc.perform(
                        get("/getAll"))
                .andExpect(status().isOk());
    }
}
