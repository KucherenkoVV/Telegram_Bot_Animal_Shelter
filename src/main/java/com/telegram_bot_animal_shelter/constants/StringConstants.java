package com.telegram_bot_animal_shelter.constants;

public abstract class StringConstants {
    public static final String START = "/start";

    public static final String HI = " привет, меня зовут бот Макс, я помогу тебе с усыновлением собаки или кошки, для этого выбери нужную кнопку!";

    public static final String INFO_ABOUT_BOT = "Информация о возможностях бота \n- Бот может показать информацию о приюте \n" +
            "- Покажет какие документы нужны \n- Бот может принимать ежедневный отчет о питомце\n" +
            "- Может передать контактные данные волонтерам для связи";
    public static final String INFO_ABOUT_SHELTER_DOG = "Наш сайт с информацией о приюте для собак \nhttps://newchance.kz/ \n" +
            "Контактные данные \nNEWCHANCEKZ@GMAIL.COM \n+7(707)897-57-27\n" +
            "Общие рекомендации \nhttps://www.royalcanin.com/ru/dogs/thinking-of-getting-a-dog/\n" +
            "";
    public static final String INFO_ABOUT_SHELTER_CAT = "Наш сайт с информацией о приюте для кошек \nhttps://newchance.kz/ \n" +
            "Контактные данные \nNEWCHANCEKZ@GMAIL.COM\n \n+7(707)897-57-27\n" +
            "Общие рекомендации \nhttps://www.royalcanin.com/ru/dogs/thinking-of-getting-a-cat/\n" +
            "";
    public static final String SCHEMA_2GIS = "Наш адрес: г. Алматы, ул. Байзакова 130." +
            "\nДля построения маршрута перейдите по ссылке: \n https://2gis.kz/geo/9429940000798774/76.912611639127135,43.255015121537561" +
            "\nПропуск на автомобиль можно оформить в пункте охраны по предварительному звонку: т. 8 (999) 277-27-07";

    public static final String SAFETY = "Техника безопасности при нахождении в приюте:" +
            "\n 1. Ходить только по обозначенным тротуарам" +
            "\n 2. Животных кормить только разрешенным кормом"+
            "\n 3. Не фотографировать со вспышкой - это может напугать питомцев" +
            "\n 4. Не пихать руки в закрытые вольеры"+
            "\n 5. Не оставлять детей одних, без присмотра";

    public static final String INFO_ABOUT_DOCUMENTS = "Для того, чтобы взять питомца из приюта вам необходимо иметь при себе:" +
            "\n1. Паспорт."+
            "\n2. Заявление о усыновлении питомца."+
            "\n3. Заполненную анкету с информацией о номере телефона, адресе проживания.";

    public static final String INFO_ABOUT_ANIMAL_WITH_DEFECTS = "Если вы хотите взять питомца с ограниченными возможностями, " +
            "ознакомьтесь с информацией: \nhttps://www.friendforpet.ru/out-articles/mify-o-zhivotnyh-invalidah/";
    public static final String INFO_ABOUT_DOGS = "Правила знакомства с собаками: \nwww.royalcanin.com/ru/dogs/understanding-your-dog/ \n" +
            "Список документов: \nПаспорт, либо другой документ, удостоверяющий личность. Заявление.\n" +
            "Список рекомендаций: \nhttps://www.royalcanin.com/ru/dogs/\n" +
            "Советы кинолога: \nhttps://centr-sobak.ru/blogs\n" +
            "Прочая информация: \nhttps://newchance.kz/\n" +
            "";

    public static final String INFO_ABOUT_CATS = "Правила знакомства с кошками \nwww.royalcanin.com/ru/cats/understanding-your-cat/ \n" +
            "Список документов: \nПаспорт, либо другой документ, удостоверяющий личность. Заявление\n" +
            "Список рекомендаций: \nhttps://www.royalcanin.com/ru/cats/\n" +
            "Прочая информация: \nhttps://newchance.kz/\n" +
            "";
    public static final String INFO_ABOUT_REPORT = "Для отчета нужна следующая информация:\n" +
            "- Фото животного.  \n" +
            "- Рацион животного\n" +
            "- Общее самочувствие и привыкание к новому месту\n" +
            "- Изменение в поведении: отказ от старых привычек, приобретение новых.\nСкопируйте следующий пример. Не забудьте прикрепить фото";

    public static final String REPORT_EXAMPLE = "Рацион: ваш текст\n" +
            "Самочувствие: ваш текст\n" +
            "Поведение: ваш текст";

    public static final String REGEX_MESSAGE = "(Рацион:)(\\s)(\\W+)\n" +
            "(Самочувствие:)(\\s)(\\W+)\n" +
            "(Поведение:)(\\s)(\\W+)";

    public static final long telegramChatVolunteers = 543337033;
}
