# run these quesries only before running the application for the first time 

CREATE DATABASE cms;

USE cms;

CREATE TABLE users (username varchar(20), password varchar(20));

CREATE TABLE contacts (name varchar(20), number int);
