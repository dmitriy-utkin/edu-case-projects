# CURRENCY EXCHANGE SERVICE

___

Приложение для практического кейса "Обмен Валюты" обучающей платформы [Skillbox](https://skillbox.ru)

## Используемые технологии

- Spring Boot 2.7
- Maven 3
- Lombok
- Mapstruct
- Liquibase
- PostgreSQL

## Требования

### JDK 17

Проект использует синтаксис Java 17. Для локального запуска вам потребуется
установленный JDK 17.

### Docker
Для запуска проекта вам потребуется установленный и запущенный Docker.
Для запуска БД(PostgreSQL) требется запустить соответствующий сервис в Docker.

## Полезные команды

### Запуск контейнера с базой данных

### Первый вариант:

```bash
cd docker
docker compose up
```

### Второй вариант:

```bash
docker run -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=postgres -d postgres
```

Пользователь для подключения к контейнеру `postgres`.

### IntelliJ IDEA

Запустите main метод класса Application

### Запросы API

Создание новой записи о валюте

```bash
curl --request POST \
  --url http://localhost:8080/api/currency/create \
  --header 'Content-Type: application/json' \
  --data '{
  "name": "Доллар Готэм-Сити",
  "nominal": 3,
  "value": 32.2,
  "isoNumCode": 1337
}'
```

Получение Валюты по id

```bash
curl --request GET \
  --url http://localhost:8080/api/currency/1333
```

Конвертация валюты по числовому коду

```bash
curl --request GET \
--url http://localhost:8080/api/currency/convert?value=100&numCode=840
```

## Что вы можете сделать при помощи этого приложения:

1) Получить полный список валют и их актуальный курс, основываясь на данных ЦБ РФ
2) Получить детальную информацию по отдельно выбранной валюте, используя ее ID
3) В автоматическом режиме приложение осуществляет обновление списка валют (и информации о них) каждые 60 минут, обращаясь к источнику (ЦБ РФ)