# Список фильмов (Movie List)

Spring Boot приложение для ведения личного списка фильмов, которые пользователь хочет посмотреть.

## Технологии

- Java 17
- Spring Boot 3.2
- Spring Security (авторизация)
- Spring Data JPA
- H2 Database (файловая)
- Thymeleaf

## Функциональность

- Регистрация и вход пользователей
- Каждый пользователь видит только свои фильмы
- Добавление, редактирование и удаление фильмов
- Фильтр по жанру
- Сортировка по году (новые сверху)
- Жанры: Комедия, Драма, Боевик, Ужасы, Мультфильм

## Запуск

```bash
cd movie-list
./mvnw spring-boot:run
```

или

```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу: http://localhost:8080

H2 Console: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:file:./data/movielist`  
User: `sa`  
Password: (пусто)

## Страницы

1. `/register` — регистрация
2. `/login` — вход
3. `/movies` — список фильмов (фильтр, сортировка, удаление)
4. `/movies/add` — добавление фильма
5. `/movies/edit/{id}` — редактирование фильма

## Структура БД

**users**: id, login, password  
**movies**: id, user_id, title, releaseYear, director, genre
