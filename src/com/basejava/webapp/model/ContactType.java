package com.basejava.webapp.model;

public enum ContactType {

    PHONE_NUMBER("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    LINKEDIN_PROFILE("Профиль LinkedIn"),
    GITHUB_PROFILE("Профиль GitHub"),
    STACKOVERFLOW_PROFILE("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
