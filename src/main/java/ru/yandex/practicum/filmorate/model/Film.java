package ru.yandex.practicum.filmorate.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {
    private long id;
    @NotBlank(message = "нужно название фльма")
    @NotNull(message = "не может быть пустым")
    private String name;
    @Size(max = 200, message = "не пиши описание фильма длиннее 200 символов")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "требуется позитивная длительность")
    private int duration;
    }
