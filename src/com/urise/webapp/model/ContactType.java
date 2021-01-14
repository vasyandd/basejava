package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний телефон"),
    SKYPE("Скайп"),
    MAIL("Почта"),
    LINKEDIN("Профиль ЛинкедИн"),
    GITHUB("Профиль Гитхаб"),
    STACKOVERFLOW("Профиль стэковерфлоу"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {return title;}
}
