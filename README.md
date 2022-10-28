# Share It. Приложение для обмена вещами. 
###### Пэт проект в рамках прохождения учебного курса Яндекс.
[![ShareIt API Tests](https://github.com/Gidrosliv/java-shareit/actions/workflows/api-tests.yml/badge.svg)](https://github.com/Gidrosliv/java-shareit/actions/workflows/api-tests.yml)
    
            
## Описание:

Бэкенд сервиса обмена вещами. В основе - работа с JPA репозиториями. API для доступа к данным. Микросервисная архитектура. Упаковано с помощью Docker.

## Функциональность:

- Создание, редактирование профиля пользователей
- Запрос на получение списка пользователей
- Поиск пользователя по идентификатору
- Удаление пользователя
- Создание и редактирование предметов
- Поиск и просмотр вещей по идентификатору
- Поиск и бронирование вещей доступных для бронирования
- Отзывы пользователей

## Тестирование проекта:

Проверить работоспособность приложения, можно с помощью [готовой коллекции тестов](https://github.com/devShurakov/java-shareit/tree/main/postman) в Postman.

## Шаблоны проектирования:

В приложении применяется один из шаблонов проектирования - Data Transfer Object(DTO Pattern),
а также - Фабрика(Factory Design Pattern).

## Как запускать приложение ?

* склонировать и открыть проект в IntelliJ IDEA 
* запустить приложение Docker
* выполнения сборку проекта mvn package
* далее в терминал запустить команду docker compose up и дождаться успешного запуска проекта в контейнерах Docker.

##  Технологический стек:
![Java 11](https://img.shields.io/badge/-Java-green) ![11](https://img.shields.io/badge/-11-orange) ![Spring Boot 2.7.2 ](https://img.shields.io/badge/-Spring%20Boot-blue) ![2.7.2 ](https://img.shields.io/badge/-2.7.2-orange) ![Postgres SQL](https://img.shields.io/badge/-Postgres%20SQL-brightgreen) ![Postgres SQL](https://img.shields.io/badge/-11--alpine%20-orange) ![Lombock](https://img.shields.io/badge/-Lombok%201.18.24-lightgrey) ![hib](https://img.shields.io/badge/-Hibernate%205.6.10%20-green) ![Apache](https://img.shields.io/badge/-Apache%20Maven%204.0.0-blue) ![Docker](https://badgen.net/badge/icon/docker?icon=docker&label) ![Git](https://badgen.net/badge/icon/github?icon=github&label)     

<a href="#" onClick="scroll(0,0); return false" title="наверх">вверх страницы</a>
