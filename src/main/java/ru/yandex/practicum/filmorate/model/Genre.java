package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Genre {

    COMEDY(1, "Комедия"),
    DRAMA(2, "Драмма"),
    CARTOON(3, "Мультфильм"),
    THRILLER(4, "Триллер"),
    DOC(5, "Документальный фильм"),
    ACTION(6, "Боевик");

    private Integer genreId;
    private String name; // подумать о ЕНАМ или хешмап

    public Integer getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

}
