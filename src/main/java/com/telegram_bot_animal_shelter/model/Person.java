package com.telegram_bot_animal_shelter.model;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {

    /**
     * Person name
     * @param name
     */
    private String name;

    /**
     * Year of birthday Person
     * @param yearsOfBirth
     */
    private int yearOfBirth;

    /**
     * Phone number Person
     * @param
     */
    private String phone;

    /**
     * Person address: City, street, home, flat
     * @param address
     */
    private String address;

    /**
     * Parameter for identity chat Person with telegram bot
     * @param chatId
     */
    private Long chatId;

    /**
     * Status of obtaining a pet by a person.
     * @see Status
     * @param status
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    public Person(String name, int yearOfBirth, String phone, String address, Long chatId, Status status) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.phone = phone;
        this.address = address;
        this.chatId = chatId;
        this.status = status;
    }

    public Person(String name, String phone, Long chatId) {
        this.name = name;
        this.phone = phone;
        this.chatId = chatId;
    }
}
