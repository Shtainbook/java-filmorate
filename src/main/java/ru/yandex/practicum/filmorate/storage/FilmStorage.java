package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    ResponseEntity<Film> create(Film film);

    ResponseEntity<Film> read(int id);

    ResponseEntity<List<Film>> readAll();

    ResponseEntity<Film> update(Film film);

    ResponseEntity<?> delete(int id);

    boolean contains(int id);

    Map<Integer, Film> getStorage();

    Film getFilm(int id);
}