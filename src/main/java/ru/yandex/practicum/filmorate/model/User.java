package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    int id;
    String email;
    String login;
    String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
}
