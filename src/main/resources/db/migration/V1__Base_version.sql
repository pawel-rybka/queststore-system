CREATE TABLE `students_classes` (
	`student_id`	INTEGER,
	`class_id`	INTEGER
);
CREATE TABLE "students" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT,
	`last_name`	TEXT,
	`phone_number`	TEXT,
	`email`	TEXT,
	`password`	TEXT,
	`coins`	INTEGER,
	`total_coins`	INTEGER
);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (1,'Dominique','Williams','003630/734-4926','dolor@laoreet.co.uk','61823',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (2,'Jemima','Foreman','003620/834-6898','magna@etultrices.net','58324',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (3,'Zeph','Massey','003630/216-5351','a.feugiat.tellus@montesnasceturridiculus.co.uk','61349',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (4,'Joseph','Crawford','003670/923-2669','lacinia.mattis@arcu.co.uk','12916',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (5,'Ifeoma','Bird','003630/465-8994','diam.duis.mi@orcitinciduntadipiscing.com','65603',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (6,'Arsenio','Matthews','003620/804-1652','semper.pretium.neque@mauriseu.net','39220',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (7,'Jemima','Cantu','003620/423-4261','et.risus.quisque@mollis.co.uk','10384',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (8,'Carol','Arnold','003630/179-1827','dapibus.rutrum@litoratorquent.com','70730',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (9,'Jane','Forbes','003670/653-5392','janiebaby@adipiscingenimmi.edu','56882',NULL,NULL);
INSERT INTO `students` (id,first_name,last_name,phone_number,email,password,coins,total_coins) VALUES (10,'Ursa','William','003620/496-7064','malesuada@mauriseu.net','91220',NULL,NULL);
CREATE TABLE `quests` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`category`	TEXT
, value INTEGER);
CREATE TABLE `purchases` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`student_id`	INTEGER,
	`piggybank_id`	INTEGER
);
CREATE TABLE `piggybanks` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`artifact_id`	INTEGER,
	`counter`	INTEGER
);
CREATE TABLE `mentors_classes` (
	`class_id`	INTEGER,
	`mentor_id`	INTEGER
);
CREATE TABLE "mentors" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT,
	`last_name`	TEXT,
	`phone_number`	TEXT,
	`email`	TEXT,
	`password`	TEXT
);
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (1,'Pál','Monoczki','003630/327-2663','pal.monoczki@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (2,'Sándor','Szodoray','003620/519-9152','sandor.szodoray@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (3,'Dániel','Salamon','003620/508-0706','daniel.salamon@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (4,'Miklós','Beöthy','003630/256-8118','miklos.beothy@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (5,'Tamás','Tompa','003630/370-0748','tamas.tompa@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (6,'Mateusz','Ostafil','003648/518-664-923','mateusz.ostafil@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (7,'Anikó','Fenyvesi','003670/111-2222','aniko.fenyvesi@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (8,'Immánuel','Fodor','003620/123-6234','immanuel.fodor@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (9,'László','Molnár','003620/222-5566','laszlo.molnar@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (10,'Mátyás','Forián Szabó','003630/111-5532','matyas.forian.szabo@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (11,'Zoltán','Sallay','003670/898-3122','zoltan.sallay@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (12,'Szilveszter','Erdős','003620/444-5555','szilveszter.erdos@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (13,'László','Terray','003670/402-2435','laszlo.terray@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (14,'Árpád','Törzsök','003630/222-1221','arpad.torzsok@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (15,'Imre','Lindi','003670/222-1233','imre.lindi@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (16,'Róbert','Kohányi','003630/123-5553','robert.kohanyi@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (17,'Przemysław','Ciąćka','003670/222-4554','przemyslaw.ciacka@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (18,'Marcin','Izworski','003670/999-2323','marcin.izworski@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (19,'Rafał','Stępień','003630/323-5343','rafal.stepien@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (20,'Agnieszka','Koszany','003630/111-5343','agnieszka.koszany@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (21,'Scooby','Dooo','003630/123-5343','mateusz.steliga@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (22,'Attila','Molnár','003670/630-0539','attila.molnar@codecool.com','1234');
INSERT INTO `mentors` (id,first_name,last_name,phone_number,email,password) VALUES (23,'fru','vsv','234','m','m');
CREATE TABLE "levels" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`expLevel`	INT NOT NULL
);
INSERT INTO `levels` (id,name,expLevel) VALUES (1,'noob',5);
INSERT INTO `levels` (id,name,expLevel) VALUES (2,'beginner',2000);
CREATE TABLE `completed_quests` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`student_id`	INTEGER,
	`quest_id`	INTEGER,
	`complete_date`	TEXT
);
CREATE TABLE `bought_artifacts` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`student_id`	INTEGER,
	`artifact_id`	INTEGER,
	`usage_date`	TEXT
);
CREATE TABLE "artifacts" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`category`	TEXT,
	`price`	INTEGER
);
INSERT INTO `artifacts` (id,name,category,price) VALUES (1,'Sword','single',12);
CREATE TABLE "admins" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT,
	`last_name`	TEXT,
	`phone_number`	TEXT,
	`email`	TEXT,
	`password`	TEXT
);
INSERT INTO `admins` (id,first_name,last_name,phone_number,email,password) VALUES (1,'Jerzy','Maradus','007650/134-4006','mjerzy@codecool.com','61823');
INSERT INTO `admins` (id,first_name,last_name,phone_number,email,password) VALUES (2,NULL,NULL,NULL,'a','a');
