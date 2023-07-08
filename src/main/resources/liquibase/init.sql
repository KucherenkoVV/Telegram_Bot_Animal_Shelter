--liquibase formatted sql

CREATE TABLE CAT
(
    name        VARCHAR NOT NULL,
    breed       VARCHAR NOT NULL,
    age         INT     NOT NULL,
    description VARCHAR NOT NULL,
    id          BIGSERIAL
        CONSTRAINT cat_pk PRIMARY KEY
);
ALTER TABLE CAT
    OWNER TO postgres;

CREATE TABLE DOG
(
    name        VARCHAR NOT NULL,
    breed       VARCHAR NOT NULL,
    age         INT     NOT NULL,
    description VARCHAR NOT NULL,
    id          BIGSERIAL
        CONSTRAINT dog_pk PRIMARY KEY
);
ALTER TABLE DOG
    OWNER TO postgres;

CREATE TABLE personCat
(
    name        VARCHAR NOT NULL,
    yearOfBirth INT     NOT NULL,
    phone       VARCHAR NOT NULL,
    address     VARCHAR NOT NULL,
    chatId      BIGINT  NOT NULL,
    status      VARCHAR NOT NULL,
    cat_id      BIGINT  NOT NULL
        constraint CAT_ID_FK
            references cat,
    id          bigserial
        constraint personCat_pk
            primary key

);
ALTER TABLE personCat
    OWNER TO postgres;

CREATE TABLE personDog
(
    name        VARCHAR NOT NULL,
    yearOfBirth INT     NOT NULL,
    phone       VARCHAR NOT NULL,
    address     VARCHAR NOT NULL,
    chatId      BIGINT  NOT NULL,
    status      VARCHAR NOT NULL,
    dog_id      BIGINT  NOT NULL
        constraint DOG_ID_FK
            references dog,
    id          bigserial
        constraint persondog_pk
            primary key

);
ALTER TABLE persondog
    OWNER TO postgres;

CREATE TABLE report
(
    chatId        BIGINT  NOT NULL,
    ration        VARCHAR NOT NULL,
    health        VARCHAR NOT NULL,
    habits        VARCHAR NOT NULL,
    days          BIGINT  NOT NULL,
    filePath      VARCHAR NOT NULL,
    fileSize      BIGINT  NOT NULL,
    caption       VARCHAR NOT NULL,
    lastMessage   date    NOT NULL,
    lastMessageMs BIGINT  NOT NULL,
    personCat_id  BIGINT
        constraint personCat_id_FK
            references personCat,
    personDog_id  BIGINT
        constraint persondog_id_FK
            references personDog,

    id            BIGSERIAL
        constraint report_pk
            primary key
);

CREATE TABLE volunteer
(
    id             BIGSERIAL primary key,
    chat_id        BIGINT,
    name           VARCHAR,
    is_active_chat BOOLEAN,
    chat_id_user   BIGINT
)