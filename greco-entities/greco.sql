

create table users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nickname varchar(32) not null,
	email varchar(64) not null,
	profile varchar (32) not null DEFAULT 'USER',
	mydata varchar(256),
	password varchar(16),
	adds TINYINT not null default 0,
	actcode varchar(6)
);

create table countries (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name varchar(48) not null
);

create table communities (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name varchar(32) not null,
	notes varchar(256),	
	zipcode varchar(5) not null,
	countrie_id int not null,
	available TINYINT not null,
	membercheck tinyint not null default 0 comment '(1) Requiere que un administrador valide para ser miembro (0) no lo requiere.',
	FOREIGN KEY (countrie_id) REFERENCES countries(id)
);

create table profiles (
	id INT AUTO_INCREMENT PRIMARY KEY,	
	profile varchar(16) not null,
	description varchar(64)
);
insert into profiles (profile,description) values ('Administrador', 'Administrador de la comunidad'), ('Copropietario', 'Copropietario de recursos');

create table resourcetypes (
	id int AUTO_INCREMENT PRIMARY KEY,
	name varchar(32) not null,
	description varchar(128)
);

create table timeunit (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name varchar(32) not null
);
-- 
	
-- Para Community poner un campo donde se indiquen notas (notes), por ejemplo, en comunidades restringidas (donde hace falta dni), indicar cómo 

create table resources (
	id int AUTO_INCREMENT PRIMARY KEY,
	name varchar(32),
	description varchar(128),
	minTime	int not null comment 'Tiempo mínimo reservable en minutos. Conforma las unidades de reserva',
	maxTime int not null comment 'Tiempo máximo reservable en minutos',
	availableFromTime varchar(5) comment 'Disponible desde las 00:00',
	availableToTime	varchar(5) comment 'Disponible hasta  las 23:59',
	beforehand int,
	resourceType_id int not null,
	community_id int not null,
	beforehandtu_id int not null DEFAULT 1 comment 'Unidad de tiempo para beforehand.',
	timeunit_id int not null DEFAULT 1 comment 'Unidad de tiempo para maxtime y mintime.',
	weekly_availability varchar(7) not null DEFAULT '1111111' comment 'Dias de la semana que está disponible. 1, disponible; 0 no disponible.',
	FOREIGN KEY (resourceType_id) REFERENCES resourcetypes(id),
	FOREIGN KEY (beforehandtu_id) REFERENCES timeunit(id),
	FOREIGN KEY (timeunit_id) REFERENCES timeunit(id),
	FOREIGN KEY (community_id) REFERENCES communities (id)
);


create table users_communities (
	id int AUTO_INCREMENT PRIMARY KEY,
	community_id int not null,
	user_id  int not null,
	profile_id  int not null,
	registerDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                  ON UPDATE CURRENT_TIMESTAMP,
	status int not null default 0,
	application varchar(256),
	FOREIGN KEY (community_id) REFERENCES communities (id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (profile_id) REFERENCES profiles (id)
);


create table exceptions (
	id int AUTO_INCREMENT PRIMARY KEY,
	resources_id int not null,
	fromDate TIMESTAMP not null,
	toDate TIMESTAMP,
	FOREIGN KEY (resources_id) REFERENCES resources (id)
);



create table reservations (
	id int AUTO_INCREMENT PRIMARY KEY,
	user_id int not null,
	resource_id int not null,
	fromDate timestamp not null default 0,
	toDate timestamp not null null default 0,
	status int not null default 0,
	status_date timestamp  not null default current_timestamp,
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (resource_id) REFERENCES resources(id)	
);



insert into timeunit set name="Horas";
insert into timeunit set name="Minutos";
insert into timeunit set name="Días";

insert into resourcetypes values (DEFAULT, 'tenis', 'Pista de tenis');
insert into resourcetypes values (DEFAULT, 'pingpong', 'Tenis de mesa');
insert into resourcetypes values (DEFAULT, 'padel', 'Pista de pádel');
insert into resourcetypes values (DEFAULT, 'sala', 'Sala de reunión');
insert into resourcetypes values (DEFAULT, 'parking', 'Parking');
insert into resourcetypes values (DEFAULT, 'citas', 'Concertación cita');
insert into resourcetypes values (DEFAULT, 'espacio', 'Espacio multiusos');

create event expirator on schedule every 5 minute do delete from reservations where status=1 and status_date < now() - interval 15 minute;
SET GLOBAL event_scheduler = ON;



