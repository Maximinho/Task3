# Инструкция по сборке и запуску
## Сборка
Сборка выполняется с помощью Maven

Для сборки необходимо выполнить в папке проекта
```
mvn clean package
```
## Запуск
После сборки в папке target создастся файл translateYandex-1.0-SNAPSHOT.jar

Для запуска необходимо выполнить
```
java -jar target\translateYandex-1.0.jar
```