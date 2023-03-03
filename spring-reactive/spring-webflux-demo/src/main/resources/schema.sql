DROP TABLE PERSON IF EXISTS;

CREATE TABLE PERSON (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    SURNAME VARCHAR(255)
);

INSERT INTO PERSON(NAME, SURNAME) VALUES('Sasha', 'Poushkine');
INSERT INTO PERSON(NAME, SURNAME) VALUES('Misha', 'Lermontov');
INSERT INTO PERSON(NAME, SURNAME) VALUES('Boris', 'Pasternak');
INSERT INTO PERSON(NAME, SURNAME) VALUES('Vasya', 'Poupkine');
INSERT INTO PERSON(NAME, SURNAME) VALUES('Joseph', 'Brodsky');

