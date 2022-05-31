CREATE TABLE Products
(
    art_number   CHAR(7) PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    color        VARCHAR(20),
    price        INTEGER     NOT NULL
        CONSTRAINT price_check CHECK ( Products.price > 0 ),
    balance      INTEGER     NOT NULL
        CONSTRAINT balance_check CHECK ( Products.balance >= 0 )
);

INSERT INTO Products
VALUES ('3251615', 'Стол кухонный', 'белый', 8000, 12),
       ('3251616', 'Стол кухонный', NULL, 8000, 15),
       ('3251617', 'Стул столовый "гусарский"', 'орех', 4000, 0),
       ('3251619', 'Стул столовый с высокой спинкой', 'белый', 3500, 37),
       ('3251620', 'Стул столовый с высокой спинкой', 'коричневый', 3500, 52);

CREATE TABLE Orders
(
    id            INTEGER PRIMARY KEY,
    creation_date DATE         NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    phone_number  VARCHAR(50),
    email         VARCHAR(50),
    address       VARCHAR(200) NOT NULL,
    order_state   CHAR(1)      NOT NULL
        CONSTRAINT state_check CHECK (Orders.order_state IN ('P', 'S', 'C')),
    shipment_Date DATE
);

INSERT INTO Orders
VALUES (1, '2020-11-20', 'Сергей Иванов', '(981)123-45-67', NULL, 'ул. Веденеева, 20-1-41', 'S', '2020-11-29'),
       (2, '2020-11-22', 'Алексей Комаров', '(921)001-22-33', NULL, 'пр. Пархоменко 51-2-123', 'S', '2020-11-29'),
       (3, '2020-11-28', 'Ирина Викторова', '(911)009-88-77', NULL, 'Тихорецкий пр. 21-21', 'P', '2020-11-29'),
       (4, '2020-12-03', 'Павел Николаев', NULL, 'pasha_nick@mail.ru', 'ул. Хлопина 3-88', 'P', '2020-11-29'),
       (5, '2020-12-03', 'Антонина Васильева', '(931)777-66-55', 'antvas66@gmail.com', 'пр. Науки, 11-3-9', 'P',
        '2020-11-29'),
       (6, '2020-12-10', 'Ирина Викторова', '(911)009-88-77', NULL, 'Тихорецкий пр. 21-21', 'P', '2020-11-29');


CREATE TABLE Positions
(
    order_id           INTEGER NOT NULL,
    product_art_number CHAR(7) NOT NULL,
    price              INTEGER NOT NULL CHECK ( Positions.price > 0 ),
    balance            INTEGER NOT NULL CHECK ( Positions.balance > 0 ),
    CONSTRAINT Positions_pk PRIMARY KEY (order_id, product_art_number),
    CONSTRAINT Orders_fk FOREIGN KEY (order_id) REFERENCES Orders (id),
    CONSTRAINT Products_fk FOREIGN KEY (product_art_number) REFERENCES Products (art_number)
);

INSERT INTO Positions
VALUES (1, '3251616', 7500, 1),
       (2, '3251615', 7500, 1),
       (3, '3251615', 8000, 1),
       (3, '3251617', 4000, 4),
       (4, '3251619', 3500, 2),
       (5, '3251615', 8000, 1),
       (5, '3251617', 4000, 4),
       (6, '3251617', 4000, 2);

-- список заказов, созданных: в ноябре, в декабре --
SELECT *
FROM Orders
WHERE creation_date >= '2020-11-01'
  AND creation_date < '2020-12-31';

-- список заказов, отгруженных: в ноябре, в декабре --
SELECT *
FROM Orders
WHERE shipment_Date >= '2020-11-01'
  AND shipment_Date < '2020-12-31'
  AND order_state = 'S';

--список клиентов: для каждого клиента должны быть выведены его ФИО, телефон и адрес электронной почты --
SELECT DISTINCT customer_name, phone_number, address, email
FROM Orders;

-- список позиций заказа с id=3 --
SELECT product_art_number
FROM Positions
WHERE order_id = 3;

-- названия товаров, включённых в заказ с id=3 --
SELECT product_name
FROM POSITIONS
         INNER JOIN PRODUCTS ON Positions.product_art_number = Products.art_number
WHERE order_id = 3;

-- список отгруженных заказов, и количество позиций в каждом из них --
SELECT id AS Order_ID, SUM(balance) AS Positions_Count
FROM Orders
         INNER JOIN Positions ON Orders.id = Positions.order_id
WHERE order_state = 'S'
GROUP BY id;

--вычисляем общую стоимость заказа --
SELECT id AS Order_ID, SUM(balance) AS Positions_Count, SUM(price) AS Price
FROM Orders
         INNER JOIN Positions ON Orders.id = Positions.order_id
WHERE order_state = 'S'
GROUP BY id;


-- Напишите запрос, фиксирующий отгрузку заказа с id=5 --
UPDATE Orders
SET order_state   = 'S',
    shipment_Date = CURRENT_DATE
WHERE id = 5;