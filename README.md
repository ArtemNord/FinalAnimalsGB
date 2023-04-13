## Информация о проекте
Необходимо организовать систему учета для питомника, 
в котором живут домашние и вьючные животные.

# Задания на использование Linux и SQL:

1. Используя команду cat в терминале операционной системы Linux, создать два файла:
Домашние животные и Вьючные животные, а затем объединить их. Просмотреть содержимое созданного файла.
Переименовать файл, дав ему новое имя (Друзья человека).
![Задание 1](/images/01.png)

2. Создать директорию, переместить файл туда.
![Задание 2](/images/02.png)

3. Подключить дополнительный репозиторий MySQL. Установить любой пакет из этого репозитория.
![Задание 3](/images/03-1.png)
![Задание 3](/images/03-2.png)

4. Установить и удалить deb-пакет с помощью dpkg.
![Задание 4](/images/04.png)

5. Нарисовать диаграмму, в которой есть класс родительский класс, домашние животные и вьючные животные, в составы которых в случае домашних животных войдут классы: собаки, кошки, хомяки, а в класс вьючные животные войдут: лошади, верблюды и ослы.
![Задание 5](/images/05.jpg)

6. В MySQL репозитории создать базу данных "Друзья человека"
```sql
CREATE DATABASE `friends_of_human` ;
```

7. Создать таблицы с иерархией из диаграммы в БД
```sql
CREATE TABLE animal_types
(
	id INT AUTO_INCREMENT PRIMARY KEY, 
	animal_type VARCHAR(20)
);

INSERT INTO animal_types (animal_type)
VALUES ('Домашние'), ('Вьючные');  


CREATE TABLE home_animals
(
	id INT AUTO_INCREMENT PRIMARY KEY,
    animal VARCHAR (20),
    animal_type_id INT,
    FOREIGN KEY (animal_type_id) REFERENCES animal_types (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO home_animals (animal, animal_type_id)
VALUES ('Кошки', 1), ('Собаки', 1), ('Хомяки', 1);
 
CREATE TABLE pack_animals
(
	id INT AUTO_INCREMENT PRIMARY KEY,
    animal VARCHAR (20),
    animal_type_id INT,
    FOREIGN KEY (animal_type_id) REFERENCES animal_types (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO pack_animals (animal, animal_type_id)
VALUES ('Лошади', 2), ('Верблюды', 2), ('Ослы', 2); 
```

9. Заполнить низкоуровневые таблицы именами животных, командами
которые они выполняют и датами рождения
```sql
CREATE TABLE cats 
(       
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(20), 
    birthday DATE,
    commands VARCHAR(50),
    animal_id int,
    Foreign KEY (animal_id) REFERENCES home_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO cats (name, birthday, commands, animal_id)
VALUES ('Тесла', '2020-01-31', 'обед', 1),
('Барсик', '2015-02-27', "спать", 1),  
('Буся', '2022-01-01', "не царапайся", 1); 

CREATE TABLE dogs 
(       
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(20), 
    birthday DATE,
    commands VARCHAR(50),
    animal_id int,
    Foreign KEY (animal_id) REFERENCES home_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO dogs (name, birthday, commands, animal_id)
VALUES ('Бобик', '2022-12-01', 'рядом, фас, голос', 2),
('Тузик', '2014-03-15', "сидеть, лежать, дай лапу", 2),
('Рекс', '2020-01-10', "рядом, сидеть, фместо", 2);

CREATE TABLE hamsters 
(       
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(20), 
    birthday DATE,
    commands VARCHAR(50),
    animal_id int,
    Foreign KEY (animal_id) REFERENCES home_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO hamsters (name, birthday, commands, animal_id)
VALUES ('Хомячидзе', '2022-12-12', '', 3),
('Бубочка', '2021-01-01', '', 3),
('Кусака', '2019-12-31', '', 3);

CREATE TABLE horses 
(       
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(20), 
    birthday DATE,
    commands VARCHAR(50),
    animal_id int,
    Foreign KEY (animal_id) REFERENCES home_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO horses (name, birthday, commands, animal_id)
VALUES ('Ветер', '2021-11-13', 'рысь, хоп', 1),
('Булочка', '2018-04-14', "тише, рысь, хоп", 1),
('Лакки', '2014-10-10', "тише, рысь, хоп", 1);

CREATE TABLE camels 
(       
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(20), 
    birthday DATE,
    commands VARCHAR(50),
    animal_id int,
    Foreign KEY (animal_id) REFERENCES home_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO camels (name, birthday, commands, animal_id)
VALUES ('Бедуин', '2015-05-11', 'вперед, стоять', 2),
('Бродяга', '2017-02-11', "вперед, стоять", 2),  
('Верблюд', '2022-10-10', "плевать", 2);

CREATE TABLE donkeys
(       
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(20), 
    birthday DATE,
    commands VARCHAR(50),
    animal_id int,
    Foreign KEY (animal_id) REFERENCES home_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO donkeys (name, birthday, commands, animal_id)
VALUES ('Иа', '2014-01-15', "вперед, стоять", 3),
('Серый', '2021-06-12', "не упрямься", 3),  
('Осел', '2020-12-19', "стоять", 3);
```

10. Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу.
```sql
SET SQL_SAFE_UPDATES = 0;
DELETE FROM camels;

SELECT name, birthday, commands, animal_id FROM horses
UNION SELECT name, birthday, commands, animal_id FROM donkeys;

CREATE TEMPORARY TABLE animals AS 
SELECT *, 'Лошади' AS animal FROM horses
UNION SELECT *, 'Ослы' AS animal FROM donkeys
UNION SELECT *, 'Собаки' AS animal FROM dogs
UNION SELECT *, 'Кошки' AS animal FROM cats
UNION SELECT *, 'Хомяки' AS animal FROM hamsters;
```

11. Создать новую таблицу “молодые животные” в которую попадут все животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью до месяца подсчитать возраст животных в новой таблице
```sql
CREATE TABLE yang_animals AS
SELECT name, birthday, commands, animal_id, TIMESTAMPDIFF(MONTH, birthday, CURDATE()) AS age_in_month
FROM animals WHERE birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);
 
SELECT * FROM yang_animals;
```

12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на прошлую принадлежность к старым таблицам.
```sql
SELECT h.name, h.birthday, h.commands, pa.animal
FROM horses h
LEFT JOIN pack_animals pa ON pa.id = h.animal_id
UNION 
SELECT d.name, d.birthday, d.commands, pa.animal
FROM donkeys d 
LEFT JOIN pack_animals pa ON pa.id = d.animal_id
UNION
SELECT c.name, c.birthday, c.commands, ha.animal
FROM cats c
LEFT JOIN home_animals ha ON ha.id = c.animal_id
UNION
SELECT d.name, d.birthday, d.commands, ha.animal
FROM dogs d
LEFT JOIN home_animals ha ON ha.id = d.animal_id
UNION
SELECT hm.name, hm.birthday, hm.commands, ha.animal
FROM hamsters hm
LEFT JOIN home_animals ha ON ha.id = hm.animal_id;
```