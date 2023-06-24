package com.telegram_bot_animal_shelter.model;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public abstract class Person {

    /**
     * Person name
     * @param name
     */
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Year of birthday Person
     * @param yearsOfBirth
     */
    @Basic
    @Column(name = "yearOfBirth", nullable = false)
    private int yearOfBirth;

    /**
     * Phone number Person
     * @param
     */
    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * Person address: City, street, home, flat
     * @param address
     */
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * Parameter for identity chat Person with telegram bot
     * @param chatId
     */
    @Basic
    @Column(name = "chatId", nullable = false)
    private Long chatId;

    /**
     * Status of obtaining a pet by a person.
     * @see Status
     * @param status
     */
    @Basic
    @Column(name = "status", nullable = false)
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
