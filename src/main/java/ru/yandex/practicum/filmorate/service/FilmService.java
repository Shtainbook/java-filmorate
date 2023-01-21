package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
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
    @Getter
    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public ResponseEntity<Film> addLikeFilm(int idFilm, int userId) {
        checkBodyFilm(idFilm, userId);
        Film temp = inMemoryFilmStorage.getFilmBase().get(idFilm);
        if (temp != null) {
            temp.getLikeFilm().add(userId);
            log.debug("Лайк к фильму успешно добавлен: " + temp + ".");
        }
       return new ResponseEntity<>(temp,HttpStatus.OK);
    }

    public ResponseEntity<Film> deleteLikeFilm(int idFilm, int userId) {
        checkBodyFilm(idFilm, userId);
        Film temp = inMemoryFilmStorage.getFilmBase().get(idFilm);
        if (temp != null) {
            temp.getLikeFilm().remove(userId);
            log.debug("Лайк к фильму успешно удален: " + temp + ".");
        }
        return new ResponseEntity<>(temp,HttpStatus.OK);
    }

    public ResponseEntity<List<Film>> showPopularFilm(int count) {

        List<Film> temp = inMemoryFilmStorage.getFilmBase().values().stream().
                sorted(((o1, o2) -> (o2.getLikeFilm().size() - o1.getLikeFilm().size()))). //сортируем от большего к меньшему
                limit(count).collect(Collectors.toList());
        log.debug("Список фильмов успешно подан!");
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    public void checkBodyFilm(int... id) {
        for (int i = 0; i < id.length; i++) {
            if (!inMemoryFilmStorage.getFilmBase().containsKey(id[i])) {
                throw new NotFoundException("Такого " + id[i] + " не содержится в базе.");
            }
        }
    }
}