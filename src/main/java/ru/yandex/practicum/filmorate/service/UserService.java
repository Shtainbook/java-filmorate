package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Getter
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public ResponseEntity<?> addFriend(int base, int friend) { // base - кто дружит, friend - кого дружат
        checkBodyUser(base, friend);
        inMemoryUserStorage.getUserBase().get(base).getFriendsUser().add(friend);
        inMemoryUserStorage.getUserBase().get(friend).getFriendsUser().add(base);
        log.debug("Дружба наладилась!");
        return new ResponseEntity<>("Дружба наладилась между " + base + " и " + friend, HttpStatus.OK);
    }

    public ResponseEntity<?> removeFriend(int base, int friend) { // base - кто дружит, friend - кого дружат
        checkBodyUser(base, friend);
        inMemoryUserStorage.getUserBase().get(base).getFriendsUser().remove(friend);
        log.debug("С дружбой покончено!");
        return new ResponseEntity<>("Дружба разладилась между " + base + " и " + friend, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> showGeneralFriend(int idBase, int idFriend) { //это пересекающиеся друзья
        checkBodyUser(idBase, idFriend);
        HashSet<Integer> baza = new HashSet<>(inMemoryUserStorage.getUserBase().get(idBase).getFriendsUser());
        baza.retainAll(inMemoryUserStorage.getUserBase().get(idFriend).getFriendsUser());
        List<User> result = new ArrayList<>();
        for (Integer userId : baza
        ) {
            result.add(inMemoryUserStorage.getUserBase().get(userId));
        }
        log.debug("Демонстрирую пересечения");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> friendZona(int id) { // это показ френд ленты
        checkBodyUser(id);
        List<User> result = new ArrayList<>();
        for (Integer idUser : inMemoryUserStorage.getUserBase().get(id).getFriendsUser()) {
            result.add(inMemoryUserStorage.getUserBase().get(idUser));
        }
        log.debug("Демонстрирую френдзону!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public void checkBodyUser(int... id) {
        for (int i = 0; i < id.length; i++) {
            if (!inMemoryUserStorage.getUserBase().containsKey(id[i])) {
                throw new NotFoundException("Такого " + id[i] + " не содержится в базе.");
            }
        }
    }
}