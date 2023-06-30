package com.telegram_bot_animal_shelter.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.telegram_bot_animal_shelter.keyboard.KeyBoardShelter;
import com.telegram_bot_animal_shelter.model.PersonCat;
import com.telegram_bot_animal_shelter.model.PersonDog;
import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.repository.PersonCatRepository;
import com.telegram_bot_animal_shelter.repository.PersonDogRepository;
import com.telegram_bot_animal_shelter.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Collections;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdateListenerTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private PersonCatRepository personCatRepository;
    @Mock
    private PersonDogRepository personDogRepository;
    @Mock
    private KeyBoardShelter keyBoardShelter;

    @InjectMocks
    private TelegramBotUpdateListener telegramBotUpdateListener;


    @PostConstruct
    public void init() {
        Report report = new Report();
        report.setDays(1L);
    }


    @Test
    public void shouldGetCommandStartTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", "/start"), Update.class);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(update.message().chat().firstName() + HI);
    }

    @Test
    public void shouldChooseCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", CAT), Update.class);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(SET_CAT_ANIMAL);
    }

    @Test
    public void shouldChooseDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", DOG), Update.class);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(SET_DOG_ANIMAL);
    }

    @Test
    public void shouldGetDriverSchemeTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", DRIVER_SCHEME), Update.class);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(SCHEMA_2GIS);
    }

    @Test
    public void shouldCheckReportDays() throws URISyntaxException, IOException {


        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", ALREADY_SEND_REPORT), Update.class);

        telegramBotUpdateListener.checkReportDays(update, update.message().chat().id(), Calendar.getInstance());
        Mockito.verify(reportRepository, times(2)).findAll();


        assertEquals(update.message().text(), ALREADY_SEND_REPORT);


    }
}