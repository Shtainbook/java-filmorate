package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmBase = new HashMap<>();
    private int filmIdGenerator = 1;

    @Override
    public ResponseEntity<Film> create(Film film) {
        FilmValidator.validationOfFilm(film);
        film.setId(filmIdGenerator++);
        filmBase.put(film.getId(), film);
        log.debug("Фильм успешно добавлен: " + film + ".");
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Film> read(int id) {
        if (!filmBase.containsKey(id)) {
            throw new NotFoundException("Ошибка в методе read (film). Не найден фильм");
        }
        log.debug("Действие read залогировано");
        return new ResponseEntity<>(filmBase.get(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Film>> readAll() {
        log.debug("Действие readAll залогировано");
        return new ResponseEntity<>(new ArrayList<>(filmBase.values()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Film> update(Film film) {
        FilmValidator.validationOfFilm(film);
        if (filmBase.containsKey(film.getId())) {
            filmBase.put(film.getId(), film);
            log.debug("Фильм успешно обновлен: " + film + ".");
            return new ResponseEntity<>(film, HttpStatus.OK);
        }
        log.debug("Фильм не обновлен: " + film + ".");
        throw new NotFoundException("Ошибка в методе update (film). Не найден фильм");
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        if (filmBase.containsKey(id)) {
            filmBase.remove(id);
            log.debug("Фильм успешно удален");
            return new ResponseEntity<>("Пользователь успешно удален.", HttpStatus.OK);
        }
        log.debug("Фильм успешно не удален");
        throw new NotFoundException("Ошибка в методе delete (film). Не найден фильм");
    }

    @Override
    public boolean contains(int id) {
        return filmBase.containsKey(id);
    }

    @Override
    public Map<Integer, Film> getStorage() {
        return filmBase;
    }

    @Override
    public Film getFilm(int id) {
        return filmBase.get(id);
    }
}
