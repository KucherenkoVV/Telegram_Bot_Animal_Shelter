package com.telegram_bot_animal_shelter.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue
    private Long id;

    private Long chatId;

    private String ration;

    private String health;

    private String habits;

    private Long days;

    private String filePath;

    private Long fileSize;

    @Lob
    private byte[] data;

    private String caption;

    private Date lastMessage;

    private Long lastMessageMs;
}
