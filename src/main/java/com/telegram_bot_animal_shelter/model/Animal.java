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
@AllArgsConstructor
@MappedSuperclass
public abstract class Animal {

    /**
     * Cat name
     * @param name
     */
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Pet breed. Like: Britain, Norwegian forest,
     * @param breed
     */
    @Basic
    @Column(name = "breed", nullable = false)
    private String breed;

    /**
     * Pet age
     * @param age
     */
    @Basic
    @Column(name = "age", nullable = false)
    private int age;

    /**
     * Additional information about the pet: habbits, health, features
     * @param description
     */
    @Basic
    @Column(name = "description", nullable = false)
    private String description;

}
