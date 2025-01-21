create table product(
    maker VARCHAR(10) NOT NULL ,
    model VARCHAR(50) NOT NULL PRIMARY KEY ,
    type VARCHAR(50) NOT NULL CHECK (type IN ('PC', 'Laptop', 'Printer'))
);
create  table  laptop(
    code INT GENERATED  BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY  ,
    model VARCHAR(50) NOT NULL,
    speed SMALLINT NOT NULL ,
    ram SMALLINT NOT NULL,
    hd REAL NOT NULL,
    price MONEY,
    screen SMALLINT NOT NULL,
    FOREIGN KEY (model) REFERENCES product(model)
);

create  table  pc (
    code INT GENERATED  BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY ,
    model VARCHAR(50) NOT NULL,
    speed SMALLINT NOT NULL,
    ram SMALLINT NOT NULL,
    hd REAL NOT NULL,
    cd VARCHAR(10) NOT NULL,
    price MONEY,
    FOREIGN KEY (model) REFERENCES product(model)
);

create  table printer(
    code INT GENERATED  BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY ,
    model VARCHAR(50) NOT NULL,
    color CHAR(1) NOT NULL CHECK ( color IN ('y', 'n')),
    type VARCHAR(10) NOT NULL CHECK (type IN ('Laser', 'Jet', 'Matrix')),
    price MONEY,
    FOREIGN KEY (model) REFERENCES product(model)
);

INSERT INTO product VALUES
                        ('Apple', 'MacBookPro', 'Laptop'),
                        ('Apple', 'MacPro', 'PC'),
                        ('Epson', 'FX-890', 'Printer'),
                        ('Dell', 'XPS 15', 'Laptop'),
                        ('HP', 'EliteDesk 800', 'PC'),
                        ('Canon', 'PIXMA MG3620', 'Printer');
INSERT INTO laptop (model, speed, ram, hd, price, screen) VALUES
                         ('MacBookPro', 3200, 16, 512, 1500.00, 15),
                         ('XPS 15', 2800, 16, 512, 1300.00, 15),
                         ('MacBookPro', 2800, 8, 256, 1200.00, 13),
                         ('EliteDesk 800', 2500, 8, 256, 800.00, 14),
                         ('MacPro', 3500, 32, 1024, 2500.00, 17);
INSERT INTO pc (model, speed, ram, hd, cd, price) VALUES
                         ('MacPro', 3500, 32, 1024, 'Blu-ray', 300.00),
                         ('MacBookPro', 3200, 16, 512, '12x', 490.00),
                         ('XPS 15', 2800, 16, 512, '24x', 1300.00);
INSERT INTO printer (model, color, type, price) VALUES
                    ('FX-890', 'y', 'Jet', 100.00),
                    ('PIXMA MG3620', 'n', 'Jet', 450.00);
-- Написать SELECT-запросы на базе данных, созданной в первом задании, для следующих задач:
-- 1 Найти номер модели, скорость и размер жесткого диска для всех ПК стоимостью менее 500 долларов.
SELECT (pc.model, pc.speed, pc.hd) FROM pc WHERE pc.price::numeric < 500.0;
-- 2 Найти производителей принтеров. Вывести поля: maker.
SELECT DISTINCT maker FROM product where type = 'Printer';
-- 3 Найти номер модели, объем памяти и размеры экранов ноутбуков, цена которых превышает 1000 долларов.
SELECT (model , hd, screen) FROM laptop where price::numeric > 1000;
--4 Найти все записи таблицы Printer для цветных принтеров.
SELECT * from printer WHERE color = 'y';
-- 5 Найти номер модели, скорость и размер жесткого диска для ПК, имеющих скорость cd 12x или 24x и цену менее 600 долларов.
SELECT (pc.model, pc.speed, pc.hd) FROM pc WHERE  cd = '12x' or cd = '24x' and price::numeric > 600;
-- 6 Указать производителя и скорость для тех ноутбуков, которые имеют жесткий диск объемом не менее 100 Гбайт.
SELECT product.maker, laptop.speed
FROM laptop
JOIN product On laptop.model = product.model
WHERE  laptop.speed >= 100;
-- 7 Найти номера моделей и цены всех продуктов (любого типа), выпущенных производителем B (латинская буква).
SELECT pc.model, pc.price FROM  pc JOIN product ON  pc.model = product.model WHERE maker LIKE 'A%'
UNION
SELECT  laptop.model, laptop.price FROM laptop  JOIN  product ON laptop.model = product.model WHERE maker LIKE 'A%'
UNION
SELECT printer.model, printer.price FROM  printer JOIN product ON printer.model = product.model WHERE maker LIKE 'A%';
--8 Найти производителя, выпускающего ПК, но не ноутбуки.
-- для проверки, я добавил:
INSERT  INTO product VALUES ('Lenovo','Legion','PC');
SELECT  product.maker FROM  product WHERE type = 'PC' AND  product.maker NOT IN (
    SELECT maker FROM product WHERE type= 'Laptop'
    );
-- 9 Найти производителей ПК с процессором не менее 450 Мгц. Вывести поля: maker.
SELECT  DISTINCT product.maker FROM product JOIN pc p on product.model = p.model WHERE speed >= 450;
-- 10 Найти принтеры, имеющие самую высокую цену. Вывести поля: model, price.
SELECT printer.model, printer.price FROM printer ORDER BY price DESC LIMIT 1;
--11 Найти среднюю скорость ноутбуков, цена которых превышает 1000 долларов.
SELECT AVG(laptop.speed) FROM laptop WHERE price::numeric > 1000;
-- 12 Найти среднюю скорость ПК, выпущенных производителем A.(Я буду указать Apple)
SELECT  AVG(pc.speed) FROM pc JOIN  product ON pc.model = product.model WHERE maker = 'Apple';
-- 13 Для каждого значения скорости процессора найти среднюю стоимость ПК с такой же скоростью. Вывести поля: скорость, средняя цена.
SELECT  speed, AVG(price::numeric)  FROM pc GROUP BY  speed;
-- 14 Найти размеры жестких дисков, совпадающих у двух и более PC. Вывести поля: hd.
SELECT pc.hd FROM  pc GROUP BY hd HAVING COUNT(*) >= 2;
-- 15 Найти пары моделей PC, имеющих одинаковые скорость процессора и RAM. В результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i), Порядок вывода полей: модель с большим номером, модель с меньшим номером, скорость, RAM.
SELECT pc1.model , pc2.model ,pc1.speed, pc1.ram FROM pc pc1 join  pc pc2 ON pc1.speed = pc2.speed
    AND pc1.ram = pc2.ram AND pc1.model < pc2.model;
-- 16 Найти модели ноутбуков, скорость которых меньше скорости любого из ПК. Вывести поля: type, model, speed.
SELECT  laptop.model FROM  laptop WHERE laptop.speed < (SELECT MAX(speed) FROM pc);
-- 17 Найти производителей самых дешевых цветных принтеров. Вывести поля: maker, price.
SELECT product.maker, printer.price FROM product join printer ON product.model = printer.model WHERE printer.color = 'y'
                                    AND printer.price = (SELECT MIN(price) FROM printer WHERE color = 'y');
-- 18 Для каждого производителя найти средний размер экрана выпускаемых им ноутбуков. Вывести поля: maker, средний размер экрана.
SELECT product.maker, AVG(laptop.screen) FROM product JOIN laptop ON product.model = laptop.model WHERE product.type = 'Laptop' group by product.maker;
-- 19 Найти производителей,  по меньшей мере три различных модели ПК. Вывести поля: maker, число моделей.
-- Для выполнение запроса , буду добавтить доп данные в таблицу
INSERT INTO product VALUES ('Apple','iMac_Mini', 'PC');
INSERT INTO pc (model, speed, ram, hd, cd, price) VALUES ('iMac_Mini', 300, 32, 1024, '24x', 300.00);
SELECT product.maker , COUNT(DISTINCT product.model) FROM product JOIN  pc ON product.model = pc.model GROUP BY product.maker HAVING  COUNT(DISTINCT pc.model) >= 3;
-- 20 Найти максимальную цену ПК, выпускаемых каждым производителем. Вывести поля: maker, максимальная цена.
SELECT product.maker, MAX(pc.price) FROM product JOIN pc ON product.model = pc.model GROUP BY product.maker;
-- 21 Для каждого значения скорости процессора ПК, превышающего 600 МГц, найти среднюю цену ПК с такой же скоростью. Вывести поля: speed, средняя цена.
SELECT pc.speed, AVG(pc.price::numeric) FROM pc WHERE speed > 600 group by pc.speed;
-- 22 Найти производителей, которые производили бы как ПК, так и ноутбуки со скоростью не менее 750 МГц. Вывести поля: maker
SELECT product.maker FROM  product
JOIN pc  ON  product.model = pc.model
JOIN  laptop ON  product.model = laptop.model
WHERE   pc.speed >= 750 AND  laptop.speed >= 750
GROUP BY product.maker;
-- 23 Перечислить номера моделей любых типов, имеющих самую высокую цену по всей имеющейся в базе данных продукции.
SELECT model
FROM (
         SELECT model, price::numeric FROM pc
         UNION ALL
         SELECT model, price::numeric FROM laptop
         UNION ALL
         SELECT model, price::numeric FROM printer
) AS all_products
WHERE price = (SELECT MAX(price) FROM (
SELECT price::numeric FROM pc
UNION ALL
     SELECT price::numeric FROM laptop
UNION ALL
     SELECT price::numeric FROM printer) AS max_price);
-- 24 Найти производителей принтеров, которые производят ПК с наименьшим объемом RAM и с самым быстрым процессором среди всех ПК, имеющих наименьший объем RAM. Вывести поля: maker
SELECT DISTINCT product.maker
FROM product
         JOIN pc ON product.model = pc.model
WHERE product.maker IN (
    SELECT DISTINCT product.maker
    FROM product
             JOIN printer ON product.model = printer.model
)
  AND pc.ram = (SELECT MIN(ram) FROM pc)
  AND pc.speed = (SELECT MAX(speed) FROM pc WHERE ram = (SELECT MIN(ram) FROM pc));





