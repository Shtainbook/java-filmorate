package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage,
                         LikeStorage likeStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
       this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT * FROM films";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Film(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_Date").toLocalDate(),
                rs.getInt("duration"),
                new HashSet<>(likeStorage.getLikes(rs.getLong("id"))),
                mpaStorage.getMpaById(rs.getInt("rating_id")),
                genreStorage.getFilmGenres(rs.getLong("id")))
        );
    }

    @Override
    public Film create(Film film) {
        InMemoryFilmStorage.isValidFilm(film);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue());
        film.setMpa(mpaStorage.getMpaById(film.getMpa().getId()));
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genre.setName(genreStorage.getGenreById(genre.getId()).getName());
            }
            genreStorage.delete(film);
            genreStorage.add(film);
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        if (film == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        String sqlQuery = "UPDATE films SET " +
                "name = ?, description = ?, release_date = ?, duration = ?, " +
                "rating_id = ? WHERE id = ?";
        if (jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()) != 0) {
            film.setMpa(mpaStorage.getMpaById(film.getMpa().getId()));
            if (film.getGenres() != null) {
                Collection<Genre> sortGenres = film.getGenres().stream()
                        .sorted(Comparator.comparing(Genre::getId))
                        .collect(Collectors.toList());
                film.setGenres(new LinkedHashSet<>(sortGenres));
                for (Genre genre : film.getGenres()) {
                    genre.setName(genreStorage.getGenreById(genre.getId()).getName());
                }
            }
            genreStorage.delete(film);
            genreStorage.add(film);
            return film;
        } else {
            throw new NotFoundException("Фильм с ID=" + film.getId() + " не найден!");
        }
    }

    @Override
    public Film getFilmById(Long filmId) {
        if (filmId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        Film film;
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", filmId);
        if (filmRows.first()) {
            Mpa mpa = mpaStorage.getMpaById(filmRows.getInt("rating_id"));
            Set<Genre> genres = genreStorage.getFilmGenres(filmId);
            film = new Film(
                    filmRows.getLong("id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate(),
                    filmRows.getInt("duration"),
                    new HashSet<>(likeStorage.getLikes(filmRows.getLong("id"))),
                    mpa,
                    genres);
        } else {
            throw new NotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        return film;
    }

    @Override
    public Film delete(Long filmId) {
        if (filmId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        Film film = getFilmById(filmId);
        String sqlQuery = "DELETE FROM films WHERE id = ? ";
        if (jdbcTemplate.update(sqlQuery, filmId) == 0) {
            throw new NotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        return film;
    }
}