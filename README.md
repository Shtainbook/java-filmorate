# java-filmorate

Добро пожаловать в Фильмотеку, которая поможет собирать списки фильмов, которые нравятся вам и вашим друзьям.

Что мы уже умеем делать в Фильмотеке?

1) Добавление фильма в фильмотеку, а так же его обновление;
2) Возможность поставить Лайк любимым кино картинам;
3) Посмотреть рейтинг фильмов на основании лайков;
4) Создание и обновление профилей пользователей;
5) Добавление друзей;
6) Удаление друзей;

![QuickDBD-export](https://user-images.githubusercontent.com/109694056/219897853-50e7b2cf-09fb-42a8-ba9a-55d2828ba446.png)

Пример запроса в БД:

Чтобы получить пользователя с id=666 необходимо составить следующий запрос:

    SELECT *
    FROM users
    WHERE user_id = 666;
