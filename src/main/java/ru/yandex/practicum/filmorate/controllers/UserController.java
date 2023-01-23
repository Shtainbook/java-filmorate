package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> read(@PathVariable int id) {
        return userService.read(id);
    }

    @GetMapping
    public ResponseEntity<List<User>> readAll() {
        return userService.readAll();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return userService.delete(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable int id, @PathVariable int friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends") // показать друзей конкретного пользователя
    public ResponseEntity<List<User>> showFriendZona(@PathVariable int id) {
        return userService.friendZona(id);
    }

    @GetMapping("{id}/friends/common/{otherId}") // показать пересечения друзей
    public ResponseEntity<List<User>> showTogetherFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.showGeneralFriend(id, otherId);
    }
}