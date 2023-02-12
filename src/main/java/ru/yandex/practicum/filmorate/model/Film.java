package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    Set <Genre> genres = new HashSet<>();
    }