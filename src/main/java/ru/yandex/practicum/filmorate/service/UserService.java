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
        checkBodyUser(base);
        checkBodyUser(friend);
        inMemoryUserStorage.getUserBase().get(base).getFriendsUser().add(friend);
        inMemoryUserStorage.getUserBase().get(friend).getFriendsUser().add(base);
        log.debug("Дружба наладилась!");
        return new ResponseEntity<>("Дружба наладилась между " + base + " и " + friend, HttpStatus.OK);
    }

    public ResponseEntity<?> removeFriend(int base, int friend) { // base - кто дружит, friend - кого дружат
        checkBodyUser(base);
        checkBodyUser(friend);
        inMemoryUserStorage.getUserBase().get(base).getFriendsUser().remove(friend);
        log.debug("С дружбой покончено!");
        return new ResponseEntity<>("Дружба разладилась между " + base + " и " + friend, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> showGeneralFriend(int idBase, int idFriend) { //это пересекающиеся друзья
        checkBodyUser(idFriend);
        checkBodyUser(idBase);
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
        List<Integer> friendsId = new ArrayList<>(inMemoryUserStorage.getUserBase().get(id).getFriendsUser());
// Евгений! Правильно ли я сделал? (по моей логике мы не можем указывать БД в условиях цикла, как элемент перебора,
// но при этом мы без проблем можем обращаться к ней внутри цикла.
// Соответственно я сделал прослойку между этими действиями)
        for (Integer idUser : friendsId) {
            result.add(inMemoryUserStorage.getUserBase().get(idUser));
        }
        log.debug("Демонстрирую френдзону!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private void checkBodyUser(int id) {
        if (!inMemoryUserStorage.getUserBase().containsKey(id)) {
            throw new NotFoundException("Такого " + id + " не содержится в базе. Ошибка в базе Юзеров");
        }
    }
}