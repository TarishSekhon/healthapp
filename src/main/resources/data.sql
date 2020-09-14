create table enrollment
(
enId integer AUTO_INCREMENT,
   name varchar(100) not null,
   activationStatus boolean,
   phoneNumber long,
   primary key(enId)
);

create table dependant
(
id integer AUTO_INCREMENT,
name varchar(100),
birthDate date,
primary key(id),
foreign key (enId) references enrollment(enId)
);
