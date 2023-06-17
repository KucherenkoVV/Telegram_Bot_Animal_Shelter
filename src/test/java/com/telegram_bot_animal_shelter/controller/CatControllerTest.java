package com.telegram_bot_animal_shelter.controller;

import com.telegram_bot_animal_shelter.controllers.CatController;
import com.telegram_bot_animal_shelter.model.Cat;
import com.telegram_bot_animal_shelter.service.CatService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for testing CatController
 * @see CatService
 * @author Artem Alekseev
 */
@SpringBootTest
@AutoConfigureMockMvc
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    /**
     * Test for <b>getById()</b> method in CatController
     * <br>
     * Mockito: when <b>CatService::getById()</b> method called, returns <b>cat</b> object
     * @throws Exception
     */
    @Test
    void getByIdCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);

        when(catService.getByIdCat(anyLong())).thenReturn(cat);

        mockMvc.perform(
                        get("/cat/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));


        verify(catService).getByIdCat(1L);
    }

    /**
     * Test for <b>save()</b> method in CatController
     * <br>
     * Mockito: when <b>CatService::create()</b> method called, returns <b>cat</b> object
     * @throws Exception
     */
    @Test
    void addCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("cat");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "cat");

        when(catService.addCat(cat)).thenReturn(cat);

        mockMvc.perform(
                        post("/cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));

        verify(catService).addCat(cat);
    }

    /**
     * Test for <b>update()</b> method in CatController
     * <br>
     * Mockito: when <b>CatService::update()</b> method called, returns <b>cat</b> object
     * @throws Exception
     */
    @Test
    void updateCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("cat new");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "cat new");

        when(catService.updateCat(cat)).thenReturn(cat);

        mockMvc.perform(
                        put("/cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));

        verify(catService).updateCat(cat);
    }

    /**
     * Test for <b>remove()</b> method in CatController
     * @throws Exception
     */
    @Test
    void deleteCat() throws Exception {
        mockMvc.perform(
                        delete("/cat/{id}", 1))
                .andExpect(status().isOk());
        verify(catService).removeByIdCat(1L);
    }

    /**
     * Test for <b>getAll()</b> method in CatController
     * <br>
     * Mockito: when <b>CatService::getAll()</b> method called, returns collection with <b>new Cat</b> object
     * @throws Exception
     */
    @Test
    void getAllCat() throws Exception {
        when(catService.getAllCat()).thenReturn(List.of(new Cat()));

        mockMvc.perform(
                        get("/cat/all"))
                .andExpect(status().isOk());
    }
}
