drop table if exists Clients cascade ;
drop table if exists Rooms cascade ;
drop table if exists Reservations cascade ;

create table Clients(
    clientID SERIAL,
    PRIMARY KEY (clientID),
    name varchar(255),
    surname varchar(255),
    email varchar(255),
    phone varchar(255)
);

create table Rooms(
    roomID SERIAL,
    PRIMARY KEY (roomID),
    number varchar(255),
    type varchar(255),
    price numeric(100),
    details text
);

create table Reservations(
    reservationID SERIAL,
    roomID integer,
    FOREIGN KEY (RoomID) references Rooms (RoomID),
    clientID integer,
    FOREIGN KEY (clientID) references Clients (clientID),
    arrivalDate date,
    departureDate date,
    status boolean
);

INSERT INTO clients (name, surname, email, phone) VALUES ('dsfgdsfg', 'dsfgdsfg', 'dsfgdsfg','dsfgdsfg');
INSERT INTO Rooms (number,type,price,details) VALUES ('dsfgdsfg', 'dsfgdsfg', 234,'dsfgdsfg');
INSERT INTO Reservations(roomid, clientid, arrivaldate, departuredate,status) VALUES (1,1,TO_DATE('2021-11-16', 'YYYY-MM-DD'),TO_DATE('2022-11-16', 'YYYY-MM-DD'),true);