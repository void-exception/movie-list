package com.example.movielist.model;

public enum Genre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    ACTION("Боевик"),
    HORROR("Ужасы"),
    CARTOON("Мультфильм");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}