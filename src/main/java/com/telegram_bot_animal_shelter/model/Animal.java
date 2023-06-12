package com.telegram_bot_animal_shelter.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Animal {

    /**
     * Cat name
     * @param name
     */
    @Column(name = "name")
    private String name;

    /**
     * Pet breed. Like: Britain, Norwegian forest,
     * @param breed
     */
    @Column(name = "breed")
    private String breed;

    /**
     * Pet age
     * @param age
     */
    @Column(name = "age")
    private int age;

    /**
     * Additional information about the pet: habbits, health, features
     * @param description
     */
    @Column(name = "description")
    private String description;

}
