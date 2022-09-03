# Приложение для обмена вещами между пользователями. Java-shareIt.
#### Учебный проект
[![ShareIt API Tests](https://github.com/Gidrosliv/java-shareit/actions/workflows/api-tests.yml/badge.svg)](https://github.com/Gidrosliv/java-shareit/actions/workflows/api-tests.yml)
    
            
Описание:
Бэкенд для сервиса обмена вещами. В основе сервиса лежит работа с репозиториями, а также реализация API и эндпоинтов для обмена данными из них. 
Реализована микросервисная архитектура. Упаковано с помощью Docker.

Технологический стэк:
Java 11, Spring Boot, Postgres SQL, REST, MockMvc, Hibernate, Docker.
https://img.shields.io/static/v1?label=<LABEL>&message=<MESSAGE>&color=<COLOR>
(https://badgen.net/docker/stars/library/mongo?icon=docker&label=stars)

Функциональность:
- Создание, редактирование профиля пользователей
- Запрос на получение списка пользователей
- Поиск пользователя по идентификатору
- Удаление пользователя
- Создание и редактирование предметов
- Поиск и просмотр вещей по идентификатору
- Поиск и бронирование вещей доступных для бронирования
- Отзывы пользователей

