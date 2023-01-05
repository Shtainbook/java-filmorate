package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
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
        log.debug("Данные о фильмах получены");
        return filmBase.isEmpty() ? new ResponseEntity<>(new ArrayList<>(filmBase.values()), HttpStatus.NOT_FOUND) : new ResponseEntity<>(new ArrayList<>(filmBase.values()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        Film temp = null;
        try {
            if (film.getId() < 1) {
                film.setId(filmIdGenerator++);
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                //throw new FilmValidationException("Релиз должен быть не ранее 1895-12-28");
                return new ResponseEntity<>(film, HttpStatus.valueOf(400));
            }
            log.debug("Фильм успешно добавлен: " + film + ".");
            temp = filmBase.put(film.getId(), film);
            //return null != temp ? new ResponseEntity<>(temp, HttpStatus.OK) : new ResponseEntity<>(temp, HttpStatus.valueOf(404));
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(film, HttpStatus.valueOf(404));
        }
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        Film temp = null;
        try {
            if (!filmBase.containsKey(film.getId())) {
                return new ResponseEntity<>(film, HttpStatus.NOT_FOUND);
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                throw new FilmValidationException("Релиз должен быть не ранее 1895-12-28");
            }

            log.debug("Фильм успешно обновлен: " + film + ".");
            temp = filmBase.put(film.getId(), film);
            //return null != temp ? new ResponseEntity<>(temp, HttpStatus.OK) : new ResponseEntity<>(temp, HttpStatus.valueOf(404));
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(temp, HttpStatus.valueOf(404));
        }
    }

    public long getFilmIdGenerator() {
        return filmIdGenerator;
    }
}