package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    int id;
    @NotNull
    @Email
    String email;
    @NotNull
    String login;
    String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
}
