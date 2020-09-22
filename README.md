# Medical-Center-Booking-System-with-Java-Javafx-SQL-server

1.JDK， JDBC， API  
Java JDK: 1.8
JDBC Drivers 6.0, Sqljdbc41.jar
controlsfx 8.40.14 API

2.About Database used in this project
This project use SQL server as database management system.
Use JDBC Drivers 6.0 as JDBC driver 
There is a .properties file in the project to save database properties.

#This is SQL server setting
driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
url=jdbc:sqlserver://127.0.0.1:1433;databaseName=Medical_Center
username=sa
password=123456

3.SQL to Create database and tables
-- Create database
CREATE DATABASE Medical_Center
Use Medical_Center

--Create table users

CREATE TABLE users(
username varchar(20) PRIMARY KEY ,
password varchar(20) NOT NULL,
flag int default 0) 

INSERT users VALUES('summer', '12345',1)
INSERT users VALUES('doctor', '12345',2)
--Create table doctors

CREATE TABLE doctors(
doctorid int PRIMARY KEY IDENTITY(1,1),
title varchar(10),
firstname varchar(20),
lastname varchar(20),
homephone varchar(20),
workphone varchar(20),
emergphone varchar(20),
email varchar(50),
address varchar(50)，
medical_history varchar(1000)
)
--Create table patients

CREATE TABLE patients(
patientid int PRIMARY KEY IDENTITY(1,1),
title varchar(10),
name varchar(50),
dateofbirth date,
phone varchar(20),
emergphone varchar(20),
email varchar(50),
address varchar(50),
medicalhistory varchar(2000) default 'N/A'
)

--Create table appointments

CREATE TABLE appointments(
apptid int PRIMARY KEY IDENTITY(1,1),
doctorid int FOREIGN KEY REFERENCES doctors(doctorid),
patientid int FOREIGN KEY REFERENCES patients(patientid),
date date,
slot int)
--Create table schedules
CREATE TABLE schedules(
 schdId int PRIMARY KEY IDENTITY(1,1),
 schdDate date,
 doctorId int FOREIGN KEY REFERENCES doctors(doctorid),
_1 int Default -1,
_2 int Default -1,
_3 int Default -1,
_4 int Default -1,
_5 int Default -1,
_6 int Default -1,
_7 int Default -1,
_8 int Default -1,
_9 int Default -1,
_10 int Default -1,
_11 int Default -1,
_12 int Default -1,
_13 int Default -1,
_14 int Default -1,
_15 int Default -1,
_16 int Default -1,
_17 int Default -1,
_18 int Default -1,
_19 int Default -1,
_20 int Default -1,
_21 int Default -1,
_22 int Default -1,
_23 int Default -1,
_24 int Default -1,
_25 int Default -1,
_26 int Default -1,
_27 int Default -1,
_28 int Default -1,
_29 int Default -1,
_30 int Default -1,
_31 int Default -1,
_32 int Default -1,
_33 int Default -1,
_34 int Default -1,
_35 int Default -1,
_36 int Default -1,
)

4.About log in
1)If you log in as receptionist, it has all right to the system.
User name: receptionist
Password: 12345
2)If you log in as doctor, it has the right to view the patient information, and view all the appointments.
User name: doctor
Password: 12345




