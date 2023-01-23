package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> read(@PathVariable int id) {
        return filmService.read(id);
    }

    @GetMapping
    public ResponseEntity<List<Film>> readAll() {
        return filmService.readAll();
    }

    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return filmService.delete(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLike(@PathVariable int id, @PathVariable int userId) {
        return filmService.addLikeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLike(@PathVariable int id, @PathVariable int userId) {
        return filmService.deleteLikeFilm(id, userId);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> showLikeList(@RequestParam(defaultValue = "10", required = false) @Positive int count) {
        return filmService.showPopularFilm(count);
    }
}