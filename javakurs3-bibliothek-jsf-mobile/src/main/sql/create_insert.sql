
use bibliothek;

drop table if exists rent_rentable;
drop table if exists rent_rentable_history;
drop table if exists rent;
drop table if exists rentable;
drop table if exists kunde;

SET SQL_SAFE_UPDATES = 0; 


drop table if exists user_role;
drop table if exists role;
drop table if exists user;

create table user (
	id int(11),
	username varchar (255),
	email varchar (255),
	password varchar (255),
	primary key (id)
);


create table role (
	id int(11),
	name varchar (255),
    description varchar (255),
	primary key (id)
);


create table user_role (
	user_id int(11),
	role_id int (11),
	primary key (user_id , role_id)
);

delete from user;
delete from role;


insert into user (id, username, password) values (1, 'gepi', 'BEsQhfKl4Ifu8TDUNPDdig=='); -- pepi
insert into user (id, username, password) values (2, 'gama', 'UQLs09R/ZWHecJeQF7h6gA=='); -- mia
insert into user (id, username, password) values (3, 'lele', 'B89Pj12LdigpFzIHFd2irQ=='); -- male

insert into role (id, name, description) values (1, 'Administrator', 'Administrator Role');
insert into role (id, name, description) values (2, 'general', 'General Role');
insert into role (id, name, description) values (3, 'Moderator', 'Moderator Role');


insert into user_role values (1, 1);
insert into user_role values (1, 2);
insert into user_role values (1, 3);

insert into user_role values (2, 2);

insert into user_role values (3, 2);

commit;

CREATE TABLE if not exists kunde
(
	id int not null AUTO_INCREMENT,
	name varchar(255) not null,
	nachname varchar(255),
	adress varchar(255) not null,
	birthDate date,
	steuernummer varchar(8),
	kunde_selector char (1),
	constraint PK primary key  (id)
); 


CREATE TABLE if not exists rentable
(
	id int not null AUTO_INCREMENT,
	titel varchar(255) not null,
	author varchar(255),
	isbn varchar(255),
    betriebssystem varchar(255),
	age tinyint(3),
	rentable_selector char (1),
	constraint PK primary key  (id)
); 


CREATE TABLE if not exists rent
(
	id int not null AUTO_INCREMENT,
	kassaZettelNummer varchar(255) not null unique,
	kunde_id int not null,
	abgeschlossen TINYINT(1), -- A value of zero is considered false. Non-zero values are considered true: http://stackoverflow.com/questions/289727/which-mysql-datatype-to-use-for-storing-boolean-values
	rentedOn date not null, -- http://dev.mysql.com/doc/refman/5.7/en/datetime.html
	rentedTill date,
	billAmount DECIMAL(5,2), -- http://dev.mysql.com/doc/refman/5.7/en/fixed-point-types.html
	constraint PK primary key  (id)
); 

create table if not exists rent_rentable (

	id int not null AUTO_INCREMENT,
	rent_id int not null,
	rentable_id int not null,
    constraint PK primary key  (id),
    constraint FK_RENT_RR foreign key (rent_id) references rent (id),
    constraint FK_RENTABLE_RR foreign key (rentable_id) references rentable (id)

);

create table if not exists rent_rentable_history (

	id int not null AUTO_INCREMENT,
	rent_id int not null,
	rentable_id int not null,
	constraint PK primary key  (id),
	constraint FK_RENT_RRH foreign key (rent_id) references rent (id),
    constraint FK_RENTABLE_RRH foreign key (rentable_id) references rentable (id)

);



insert into kunde values (1, 'Peter', 'Meier', 'Gudrunstr. 21, 1020 Wien', '1992-11-03', null, 'N');
insert into kunde values (2, 'Stefan', 'Schmidt', 'Börsegasse. 11, 1010 Wien', '1951-07-12', null, 'N');


insert into rentable (id, titel, author, isbn, betriebssystem, rentable_selector) values (1, 'Star Wars', 'Stephen Spielberg', null, 0, 'D');
insert into rentable (id, titel, author, isbn, betriebssystem, rentable_selector) values (2, 'Greatest Hits', 'Queen', null, 0, 'C');
insert into rentable (id, titel, author, isbn, betriebssystem, rentable_selector) values (3, 'Introduction into Computer Science', 'David Myer', 'ISBN 123456789', 0, 'B');
insert into rentable (id, titel, author, isbn, betriebssystem, rentable_selector) values (4, 'Advanced Computer Algorithms', 'David Myer', 'ISBN 1234567810', 0, 'B');


insert into rent (id, kassaZettelNummer, kunde_id , abgeschlossen,rentedOn, rentedTill, billAmount) values (1, '#KZKB 00090008912', 1, 0, '2017-01-19', '2017-02-01', 11.90);
insert into rent (id, kassaZettelNummer, kunde_id , abgeschlossen,rentedOn, rentedTill, billAmount) values (2, '#KZKB 00090008913', 2, 0, '2017-01-01', '2017-01-14', 6.60);
insert into rent (id, kassaZettelNummer, kunde_id , abgeschlossen,rentedOn, rentedTill, billAmount) values (3, '#KZKB 00090008914', 1, 1, '2016-10-20', '2016-11-03', 19.60);

insert into rent_rentable values (1,1,1);
insert into rent_rentable values (2,3,2);
insert into rent_rentable values (3,3,3);

SET SQL_SAFE_UPDATES = 1; 


ALTER TABLE kunde ENGINE = InnoDB; -- http://dev.mysql.com/doc/refman/5.7/en/converting-tables-to-innodb.html
ALTER TABLE rentable ENGINE = InnoDB; -- http://dev.mysql.com/doc/refman/5.7/en/converting-tables-to-innodb.html
ALTER TABLE rent ENGINE = InnoDB; -- http://dev.mysql.com/doc/refman/5.7/en/converting-tables-to-innodb.html
ALTER TABLE rent_rentable ENGINE = InnoDB; -- http://dev.mysql.com/doc/refman/5.7/en/converting-tables-to-innodb.html
ALTER TABLE rent_rentable_history ENGINE = InnoDB; -- http://dev.mysql.com/doc/refman/5.7/en/converting-tables-to-innodb.html



commit;
