package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {

    final static Map<Integer, Film> filmBase = new HashMap<>();

    @GetMapping("/films")
    public static List<Film> getFilms() {
        log.debug("Данные о фильмах получены");
        return new ArrayList<>(filmBase.values());
    }

    @PostMapping("/film")
    public static void addFilm(@Valid @NotNull @RequestBody Film film) {
        if (!validationOfFilm(film)) {
            throw new ValidationException("Тут какой-то текст по валдиц");
        }
        filmBase.put(film.getId(), film);
        log.debug("Фильм успешно добавлен: " + film + ".");
    }

    @PutMapping("/update-film")
    public static void updateFilm(@Valid @NotNull @RequestBody Film film) {
        if (!validationOfFilm(film)) {
            throw new ValidationException("Тут какой-то текст по валдиц");
        }
        filmBase.put(film.getId(), film);
        log.debug("Фильм успешно обновлен: " + film + ".");
    }

    public static boolean validationOfFilm(Film film) {
        if (film.getName().isBlank()) return false;
        if (film.getDescription().length() > 200) return false;
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) return false;
        if (film.getDuration().isNegative()) return false;
        return true;
    }
}