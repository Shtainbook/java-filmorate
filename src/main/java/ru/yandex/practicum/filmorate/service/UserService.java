package ru.yandex.practicum.filmorate.service;

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

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public ResponseEntity<?> addFriend(int base, int friend) { // base - кто дружит, friend - кого дружат
        checkBodyUser(base);
        checkBodyUser(friend);
        inMemoryUserStorage.addToFriend(base, friend);
        inMemoryUserStorage.addToFriend(friend, base);
        log.debug("Дружба наладилась!");
        return new ResponseEntity<>("Дружба наладилась между " + base + " и " + friend, HttpStatus.OK);
    }

    public ResponseEntity<?> removeFriend(int base, int friend) { // base - кто дружит, friend - кого дружат
        checkBodyUser(base);
        checkBodyUser(friend);
        inMemoryUserStorage.deleteFromFriend(base, friend);
        log.debug("С дружбой покончено!");
        return new ResponseEntity<>("Дружба разладилась между " + base + " и " + friend, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> showGeneralFriend(int idBase, int idFriend) { //это пересекающиеся друзья
        checkBodyUser(idFriend);
        checkBodyUser(idBase);
        HashSet<Integer> baza = new HashSet<>(inMemoryUserStorage.getFriends(idBase));
        baza.retainAll(inMemoryUserStorage.getFriends(idFriend));
         List<User> result = new ArrayList<>();
        for (Integer userId : baza
        ) {
            result.add(inMemoryUserStorage.getUser(userId));
        }
        log.debug("Демонстрирую пересечения");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> friendZona(int id) { // это показ френд ленты
        checkBodyUser(id);
        List<User> result = new ArrayList<>();
        List<Integer> friendsId = new ArrayList<>(inMemoryUserStorage.getFriends(id));
        for (Integer idUser : friendsId) {
            result.add(inMemoryUserStorage.getUser(idUser));
        }
        log.debug("Демонстрирую френдзону!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private void checkBodyUser(int id) {
        if (!inMemoryUserStorage.contains(id)) {
            throw new NotFoundException("Такого " + id + " не содержится в базе. Ошибка в базе Юзеров");
        }
    }

    public b


    public ResponseEntity<User> create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public ResponseEntity<User> read(int id) {
        return inMemoryUserStorage.read(id);
    }

    public ResponseEntity<List<User>> readAll() {
        return inMemoryUserStorage.readAll();
    }

    public ResponseEntity<User> update(User user) {
        return inMemoryUserStorage.update(user);
    }

    public ResponseEntity<?> delete(int id) {
        return inMemoryUserStorage.delete(id);
    }
}