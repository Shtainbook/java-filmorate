package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum MpaRating {

    G("G","у фильма нет возрастных ограничений",1),
    PG("PG","детям рекомендуется смотреть фильм с родителями",2),
    PG13("PG-13","детям до 13 лет просмотр не желателен",1),
    R("R","лицам до 17 лет просматривать фильм можно только в присутствии взрослого",1),
    NC17("NC-17","лицам до 18 лет просмотр запрещён",1);

    private String ratingName;
    private String description;
    private Integer id;

    public String getRatingName() {
        return ratingName;
    }

    public String getDescription() {
        return description;
    }

     public Integer getId() {
        return id;
    }

}
