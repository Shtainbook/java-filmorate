package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public ResponseEntity<Film> addLikeFilm(int idFilm, int userId) {
        checkBodyFilm(userId);
        checkBodyFilm(idFilm);
        Film temp = inMemoryFilmStorage.getFilm(idFilm);
        if (temp != null) {
            temp.getLikeFilm().add(userId);
            log.debug("Лайк к фильму успешно добавлен: " + temp + ".");
        }
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    public ResponseEntity<Film> deleteLikeFilm(int idFilm, int userId) {
        checkBodyFilm(idFilm);
        checkBodyFilm(userId);
        Film temp = inMemoryFilmStorage.getFilm(idFilm);
        if (temp != null) {
            temp.getLikeFilm().remove(userId);
            log.debug("Лайк к фильму успешно удален: " + temp + ".");
        }
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    public ResponseEntity<List<Film>> showPopularFilm(int count) {

        List<Film> temp = inMemoryFilmStorage.getStorage().values().stream().
                sorted(((o1, o2) -> (o2.getLikeFilm().size() - o1.getLikeFilm().size()))). //сортируем от большего к меньшему
                        limit(count).collect(Collectors.toList());
        log.debug("Список фильмов успешно подан!");
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    private void checkBodyFilm(int id) {
        if (!inMemoryFilmStorage.contains(id)) {
            throw new NotFoundException("Такого " + id + " не содержится в базе. Ошибка в базе фильмов.");
        }
    }

    public ResponseEntity<Film> create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public ResponseEntity<Film> read(int id) {
        return inMemoryFilmStorage.read(id);
    }

    public ResponseEntity<List<Film>> readAll() {
        return inMemoryFilmStorage.readAll();
    }

    public ResponseEntity<Film> update(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public ResponseEntity<?> delete(int id) {
        return inMemoryFilmStorage.delete(id);
    }
}
