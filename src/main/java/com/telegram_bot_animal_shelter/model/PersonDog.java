package com.telegram_bot_animal_shelter.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonDog {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int yearOfBirth;

    private String phone;

    private String address;

    private Long chatId;

    private Status status;
}
