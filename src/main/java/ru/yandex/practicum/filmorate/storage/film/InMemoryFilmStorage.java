package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films;
    private Long currentId;

    public InMemoryFilmStorage() {
        currentId = 0L;
        films = new HashMap<>();
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        if (isValidFilm(film)) {
            film.setId(++currentId);
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с ID=" + film.getId() + " не найден!");
        }
        if (isValidFilm(film)) {
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film getFilmById(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        return films.get(filmId);
    }

    @Override
    public Film delete(Long filmId) {
        if (filmId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        return films.remove(filmId);
    }

 public static boolean isValidFilm(Film film) {

        if (film == null) {
            log.error("ошибка зафиксирована: film - " + null);
            throw new ValidationException(ValidationException.class + " Отсутствует запись о фильме");
        }
        if (film.getName().isBlank()) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new ValidationException(ValidationException.class + " Название не должно быть пустым");
        }
        if (film.getDescription().length() > 200 || (film.getDescription().isEmpty())) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new ValidationException(ValidationException.class + " Описание должно быть до 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new ValidationException(ValidationException.class + " Релиз должен быть не ранее 1895-12-28");
        }
        if (film.getDuration() <= 0) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new ValidationException(ValidationException.class + " Длительность должна быть позитивной");

        }
        return true;
    }
}
