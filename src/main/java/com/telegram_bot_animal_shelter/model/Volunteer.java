package com.telegram_bot_animal_shelter.model;


import com.pengrad.telegrambot.model.Message;
import lombok.*;

import javax.persistence.*;

/**
 * Entity for volunteers.
 * @author: Kucherenko V.V.
 * @version 0.1.1
 */

@Entity
@Table(name = "volunteer")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Volunteer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "chat_id_user")
    private Long chatIdUser;

    public Volunteer(Long chatId, String name, Long chatIdUser) {
        this.chatId = chatId;
        this.name = name;
        this.chatIdUser = chatIdUser;
    }
}
