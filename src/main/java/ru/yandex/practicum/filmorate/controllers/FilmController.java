package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> filmBase = new HashMap<>();
    long filmIdGenerator = 1;

    @GetMapping
    public ResponseEntity<List<Film>> getFilms() {
        log.debug("Данные о фильмах получены.");
        return filmBase.isEmpty() ? new ResponseEntity<>(new ArrayList<>(filmBase.values()), HttpStatus.NOT_FOUND) : new ResponseEntity<>(new ArrayList<>(filmBase.values()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        try {
            if (film.getId() < 1) {
                film.setId(filmIdGenerator++);
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.debug("Проблема с датой выхода фильма.");
                return new ResponseEntity<>(film, HttpStatus.valueOf(400));
            }
            filmBase.put(film.getId(), film);
            log.debug("Фильм успешно добавлен: " + film + ".");
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Проблема с добавлением фильма.");
            return new ResponseEntity<>(film, HttpStatus.valueOf(404));
        }
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        try {
            if (!filmBase.containsKey(film.getId())) {
                log.debug("Проблема с обновлением. Не соответсвует ID.");
                return new ResponseEntity<>(film, HttpStatus.NOT_FOUND);
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.debug("Проблема с обновлением. Не соответсвует дата выхода.");
                throw new FilmValidationException("Релиз должен быть не ранее 1895-12-28");
            }
            filmBase.put(film.getId(), film);
            log.debug("Фильм успешно обновлен: " + film + ".");
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Проблема с обновлением. Фильм не обновлен.");
            return new ResponseEntity<>(film, HttpStatus.valueOf(404));
        }
    }

    public long getFilmIdGenerator() {
        return filmIdGenerator;
    }
}