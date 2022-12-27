package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    @Test
    public void validationOfShouldReturnFalseWhenFilmNameIsBlank() {
        assertFalse(FilmController.validationOfFilm(new Film(1, " ", "эротодрамма", LocalDate.of(2005, 12, 22), Duration.ofMinutes(45))));
    }

    @Test
    public void validationOfShouldReturnFalseWhenDescrptToMuchLong() {
        assertFalse(FilmController.validationOfFilm(new Film(1, "медвежуть", "путинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегда", LocalDate.of(2005, 12, 22), Duration.ofMinutes(45))));
    }

    @Test
    public void validationOfShouldReturnFalseWhenDateBefore1985() {
        assertFalse(FilmController.validationOfFilm(new Film(1, "медвежуть", "эротодрамма", LocalDate.of(1982, 12, 22), Duration.ofMinutes(45))));
    }

    @Test
    public void validationOfShouldReturnFalseWhenDurationIsMinus() {
        assertFalse(FilmController.validationOfFilm(new Film(1, "медвежуть", "эротодрамма", LocalDate.of(1999, 12, 22), Duration.ofMinutes(-2))));
    }

    @Test
    public void validationOfShouldReturnTrueWhenAllFine() {
        assertTrue(FilmController.validationOfFilm(new Film(1, "медвежуть", "эротодрамма", LocalDate.of(2005, 12, 22), Duration.ofMinutes(45))));
    }
}