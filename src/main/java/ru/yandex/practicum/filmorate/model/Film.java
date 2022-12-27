package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    int id;
    @NotNull
    String name;
    String description;
    @NotNull
    LocalDate releaseDate;
    @NotNull
    Duration duration;
}
