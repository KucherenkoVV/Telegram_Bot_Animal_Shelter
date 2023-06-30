package com.telegram_bot_animal_shelter.controllers;

import com.telegram_bot_animal_shelter.model.PersonCat;
import com.telegram_bot_animal_shelter.service.impl.PersonCatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Class of PersonCatController.
 * @author Kucherenko.vv
 * @version 0.0.1
 */
@RestController
@RequestMapping("person-cat")
public class PersonCatController {

    private final PersonCatServiceImpl personCatService;

    public PersonCatController(PersonCatServiceImpl personCatService) {
        this.personCatService = personCatService;
    }

    @Operation(summary = "Получение пользователя, усыновителя кота,  по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, усыновитель кота, найден по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonCat.class)
                            )
                    )
            },
            tags = "PersonCat"
    )
    @GetMapping("/{id}")
    public PersonCat getById(@Parameter(description = "PersonCat id") @PathVariable Long id) {
        return this.personCatService.getByIdPersonCat(id);
    }

    @Operation(summary = "Создание пользователя, усыновителя кота",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    description = "Пользователь, усыновитель кота, создан",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonCat.class)
                    )
            ),
            tags = "PersonCat"
    )
    @PostMapping()
    public PersonCat save(@RequestBody PersonCat personCat) {
        return this.personCatService.addPersonCat(personCat);
    }

    @Operation(summary = "Изменение данных пользователя, усыновителя кота",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    description = "Данные пользователя, усыновителя кота, изменены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonCat.class)
                    )
            ),
            tags = "PersonCat"
    )
    @PutMapping
    public PersonCat update(@RequestBody PersonCat personCat) {
        return this.personCatService.addPersonCat(personCat);
    }

    @Operation(summary = "Удаление пользователя, усыновителя кота, по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, усыновитель кота, удален по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonCat.class)
                            )
                    )
            },
            tags = "PersonCat"
    )
    @DeleteMapping("/{id}")
    public void remove(@Parameter(description = "PersonCat id")@PathVariable Long id) {
        this.personCatService.removeByIdPersonCat(id);
    }

    @Operation(summary = "Просмотр всех пользователей, усыновителей кота",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получены все пользователи, усыновители кота",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonCat.class)
                            )
                    )
            },
            tags = "PersonCat"
    )
    @GetMapping("/all")
    public Collection<PersonCat> getAll() {
        return this.personCatService.getAllPersonCat();
    }
}
