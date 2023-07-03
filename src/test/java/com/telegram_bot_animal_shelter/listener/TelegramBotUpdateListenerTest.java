package com.telegram_bot_animal_shelter.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.telegram_bot_animal_shelter.handlers.ReportHandler;
import com.telegram_bot_animal_shelter.keyboard.KeyBoardShelter;
import com.telegram_bot_animal_shelter.model.Report;
import com.telegram_bot_animal_shelter.repository.PersonCatRepository;
import com.telegram_bot_animal_shelter.repository.PersonDogRepository;
import com.telegram_bot_animal_shelter.repository.ReportRepository;
import com.telegram_bot_animal_shelter.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static com.telegram_bot_animal_shelter.constants.StringConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdateListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private KeyBoardShelter keyBoardShelter;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private PersonCatRepository personCatRepository;

    @Mock
    private PersonDogRepository personDogRepository;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private TelegramBotUpdateListener telegramBotUpdateListener;

    private ReportHandler reportHandler;


    @BeforeEach
    public void init() {
        reportHandler = new ReportHandler(reportRepository, reportService, telegramBot);
        reportRepository.save(new Report(123L));
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
    public void shouldGetInfoForSafetyTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", FOR_SAFETY), Update.class);
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
        assertThat(actual.getParameters().get("text")).isEqualTo(SAFETY);
    }

    @Test
    public void shouldGetInfoAboutDocumentsTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", DOCUMENTS), Update.class);
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
        assertThat(actual.getParameters().get("text")).isEqualTo(INFO_ABOUT_DOCUMENTS);
    }

    @Test
    public void shouldGetInfoAboutPetWithDefectsTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", GET_ANIMAL_WITH_DEFECTS), Update.class);
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
        assertThat(actual.getParameters().get("text")).isEqualTo(INFO_ABOUT_ANIMAL_WITH_DEFECTS);
    }

    @Test
    public void shouldGetSendReportHelperTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", SEND_REPORT), Update.class);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(REPORT_EXAMPLE);
    }

    @Test
    public void shouldGetInfoAboutBotTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", INFO_ABOUT_BOT_BUTTON), Update.class);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot, times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(INFO_ABOUT_BOT);
    }

    @Test
    public void shouldSendMessageVolunteerTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", SEND_MESSAGE_VOLUNTEER), Update.class);
        URL url = new URL(VOLUNTEER_URL);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot, times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(VOLUNTEER_QUESTION + url);
    }

    @Test
    public void shouldGetEmptyMessageTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdateListener.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", EMPTY), Update.class);
        URL url = new URL(VOLUNTEER_URL);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        keyBoardShelter = new KeyBoardShelter(telegramBot);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdateListener.process(Collections.singletonList(update));

        Mockito.verify(telegramBot, times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(update.message().chat().id());
        assertThat(actual.getParameters().get("text")).isEqualTo(EMPTY_MESSAGE);
    }
}