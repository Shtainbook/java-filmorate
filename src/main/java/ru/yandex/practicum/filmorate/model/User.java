package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private int id;
    @Email(message = "почта не подходит")
    @NotBlank(message = "введи почту")
    @NotNull(message = "введи почту")
    private String email;
    @Pattern(regexp = "\\S*$")
    @NotBlank(message = "нужно поправить логин")
    @NotNull(message = "введи логин")
    private String login;
    private String name;
    @Past(message = "Нельзя родиться в будущем")
    private LocalDate birthday;
    private final Set<Integer> friendsUser = new HashSet<>();
}