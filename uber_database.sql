create database if not exists uber_database;

use uber_database;

create table user(
	user_id int not null auto_increment primary key,
	name varchar(30) not null,
    surname varchar(30) not null,
    accountnumber varchar(26) not null,
    telephonenumber varchar(9) not null,
    mail varchar(40) not null,
    rating float not null
);

create table driver(
	driver_id int not null auto_increment primary key,
	carlicenseid varchar(20) not null,
    user_id int not null
);

create table car(
	car_id int not null auto_increment primary key,
    licenseplate varchar(10) not null,
    driver_id int not null
);

/* Foreign keys */
alter table driver add foreign key(user_id) references user(user_id);
alter table car add foreign key(driver_id) references driver(driver_id);

/* Input*/

insert into user values(null,"Rafal","Widziszewski","11111111111111111111111111","888255448","rafal.widziszewski@gmail.com",4);
insert into user values(null,"Michal","Switala","22111111111111111111111111","446225788","michal.switala@gmail.com",5);
insert into user values(null,"Jan","Kowalski","11111111133311111111111111","888445448","jan.kowalski@gmail.com",3.2);
insert into user values(null,"Nowak","Mateusz","22111111111441111111111111","446225998","nowak.mateusz@gmail.com",4.5);
insert into user values(null,"Aleksandra","Kowalska","55555111111111111111111111","888999448","aleksandra.kowalska@gmail.com",2.4);
insert into user values(null,"Julian","Slowacki","22111111111111111771111111","446543788","julian.slowacki@gmail.com",5);

insert into driver values(null,"05412/25/5678",3);
insert into driver values(null,"06789/43/5998",4);
insert into driver values(null,"05442/12/5458",5);

insert into car values(null,"KRK75TM",1);
insert into car values(null,"GA81TL",2);
insert into car values(null,"WWT21ON",3);
