package com.telegram_bot_animal_shelter.controllers;

import com.telegram_bot_animal_shelter.model.Cat;
import com.telegram_bot_animal_shelter.service.CatService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class CatControllerTest
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@WebMvcTest(CatController.class)
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    /**
     * Testing method getById cats in the controller class
     */
    @Test
    void getByIdCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);

        when(catService.getByIdCat(anyLong())).thenReturn(cat);

        mockMvc.perform(
                        get("/cat/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        verify(catService).getByIdCat(1L);
    }

    /**
     * Testing method adding cats in the controller class
     */
    @Test
    void addCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(null);
        cat.setName("Кот");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "Кот");

        when(catService.addCat(cat)).thenReturn(cat);

        mockMvc.perform(
                        post("/cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(catService).addCat(cat);
    }

    /**
     * Testing method updates cats in the controller class
     */
    @Test
    void updateCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(null);
        cat.setName("Кот");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "Кот");

        when(catService.updateCat(cat)).thenReturn(cat);

        mockMvc.perform(
                        put("/cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(catService).updateCat(cat);
    }

    /**
     * Testing method deleteById cats in the controller class
     */
    @Test
    void deleteByIdCat() throws Exception {
        mockMvc.perform(
                        delete("/cat/{id}", 1))
                .andExpect(status().isOk());
        verify(catService).removeByIdCat(1L);
    }

    /**
     * Testing method get all cats in the controller class
     */
    @Test
    void getAllCat() throws Exception {
        when(catService.getAllCat()).thenReturn(List.of(new Cat()));

        mockMvc.perform(
                        get("/cat/all"))
                .andExpect(status().isOk());
    }
}
