package com.telegram_bot_animal_shelter.controllers;

import com.telegram_bot_animal_shelter.model.PersonDog;
import com.telegram_bot_animal_shelter.service.impl.PersonDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Class of PersonDogController.
 * @author Kucherenko.vv
 * @version 0.0.1
 */
@RestController
@RequestMapping("person-dog")
public class PersonDogController {

    private final PersonDogService personDogService;

    public PersonDogController(PersonDogService personDogService) {
        this.personDogService = personDogService;
    }

    @Operation(summary = "Получение пользователя, усыновителя собаки,  по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, усыновитель собаки, найден по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonDog.class)
                            )
                    )
            },
            tags = "PersonDog"
    )
    @GetMapping("/{id}")
    public PersonDog getById(@Parameter(description = "PersonDog id")@PathVariable Long id) {
        return this.personDogService.getByIdPersonDog(id);
    }

    @Operation(summary = "Создание пользователя, усыновителя собаки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    description = "Пользователь, усыновитель собаки, найден",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonDog.class)
                    )
            ),
            tags = "PersonDog"
    )
    @PostMapping
    public PersonDog save(@RequestBody PersonDog personDog) {
        return this.personDogService.addPersonDog(personDog);
    }

    @Operation(summary = "Изменение данных пользователя, усыновителя кота",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    description = "Данные пользователя, усыновителя собаки, изменены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonDog.class)
                    )
            ),
            tags = "PersonDog"
    )
    @PutMapping
    public PersonDog update(@RequestBody PersonDog personDog) {
        return this.personDogService.updatePersonDog(personDog);
    }

    @Operation(summary = "Удаление пользователей по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, удаленный по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonDog.class)
                            )
                    )
            },
            tags = "PersonDog"
    )
    @DeleteMapping("/{id}")
    public void remove(@Parameter(description = "PersonDog id") @PathVariable Long id) {
        this.personDogService.removeByIdPersonDog(id);
    }

    @Operation(summary = "Просмотр всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все пользователи, либо определенные пользователи по chat_id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonDog.class)
                            )
                    )
            },
            tags = "PersonDog"
    )
    @GetMapping("/all")
    public Collection<PersonDog> getAll() {
            return this.personDogService.getAllPersonDog();
    }
}
