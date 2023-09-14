# REST-сервис отслеживания почтовых отправлений
### Список операций, поддерживаемых сервисом:
  - регистрации почтового отправления,
  - его прибытие в промежуточное почтовое отделение,
  - его убытие из почтового отделения,
  - его получение адресатом,
  - просмотр статуса и полной истории движения почтового отправления.


### Используемые фреймворки и библиотеки:
* Spring Boot
* Spring Web
* Spring Data JPA (Hibernate)
* Spring Security (JWT Authentication)
* Maven
* Liquibase
* Validation (Hibernate Validator)
* Open API (Swagger)
* Lombok
* Spring Test (JUnit 5, Mockito)
* Testcontainers

В качестве СУБД используется PostgreSQL. 
 
**Для запуска сервиса в Docker-контейнере необходимо:**
- запустить терминал, находясь в каталоге проекта;
- собрать проект командой (используется Maven Wrapper): 
  - `./mvnw install` для Unix
  - `mvnw.cmd install` для Windows
- выполнить команду `docker compose up` (вместе с сервисом будет запущен контейнер с базой данных)

Для удобства работы с эндпоинтами можно воспользоваться Swagger UI доступным по адресу:
```
{rootUrl}/swagger-ui/index.html

на локальной машине:
http://localhost:8082/swagger-ui/index.html
```
