package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(Long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film delete(Long filmId) {
        return filmStorage.delete(filmId);
    }


    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film != null) {
            if (userStorage.getUserById(userId) != null) {
                likeStorage.addLike(filmId, userId);
            } else {
                throw new NotFoundException("Пользователь c ID=" + userId + " не найден!");
            }
        } else {
            throw new NotFoundException("Фильм c ID=" + filmId + " не найден!");
        }
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film != null) {
            if (film.getLikes().contains(userId)) {
                likeStorage.deleteLike(filmId, userId);
            } else {
                throw new NotFoundException("Лайк от пользователя c ID=" + userId + " не найден!");
            }
        } else {
            throw new NotFoundException("Фильм c ID=" + filmId + " не найден!");
        }
    }

    public List<Film> getPopular(Integer count) {
        if (count < 1) {
            throw new ValidationException("Количество фильмов для вывода не должно быть меньше 1");
        }
        return likeStorage.getPopular(count);
    }
}
