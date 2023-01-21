package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {
    private int id;
    @NotBlank(message = "нужно название фльма")
    @NotNull(message = "не может быть пустым")
    private String name;
    @Size(max = 200, message = "не пиши описание фильма длиннее 200 символов")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "требуется позитивная длительность")
    private int duration;
    private final Set<Integer> likeFilm = new HashSet<>();
    }