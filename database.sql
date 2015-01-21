 create database countries;

 connect countries;
 
 CREATE TABLE COUNTRIES (id INT, NAME VARCHAR(100), POPULATION INT);
 
 INSERT INTO COUNTRIES(id, NAME, POPULATION) VALUES (1,'SPAIN', 45000000);
 
 INSERT INTO COUNTRIES(id, NAME, POPULATION) VALUES (2,'GERMANY', 90000000);
 
 
 delimiter //
 
CREATE PROCEDURE spanish (OUT population_out INT)
 BEGIN
 SELECT COUNT(*) INTO population_out FROM countries;
 END//
 
 
 delimiter ;
 
 CALL simpleproc(@a);
 
 CALL simpleproc(@a);