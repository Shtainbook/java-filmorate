package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> filmBase = new HashMap<>();

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Данные о фильмах получены");
        return new ArrayList<>(filmBase.values());
    }

    @PostMapping
    public void addFilm(@Valid @NotNull @RequestBody Film film) {
        FilmValidator.validationOfFilm(film);

        filmBase.put(film.getId(), film);
        log.debug("Фильм успешно добавлен: " + film + ".");
    }

    @PutMapping
    public void updateFilm(@Valid @NotNull @RequestBody Film film) {
        FilmValidator.validationOfFilm(film);

        filmBase.put(film.getId(), film);
        log.debug("Фильм успешно обновлен: " + film + ".");
    }
}