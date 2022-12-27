package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    int id;
    @NotNull
    @NotBlank
    String name;
    String description;
    @NotNull
    LocalDate releaseDate;
    @NotNull
    Duration duration;
}
