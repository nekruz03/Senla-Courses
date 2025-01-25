CREATE TABLE room(
    room_number SMALLINT NOT NULL PRIMARY KEY,
    room_status VARCHAR(20) CHECK ( room_status IN ('SERVICED','REPAIRABLE')),
    room_type VARCHAR(20) CHECK (room_type IN ('STANDARD', 'DELUXE', 'VIP')),
    capacity  SMALLINT,
    number_of_stars SMALLINT CHECK (number_of_stars IN ('1','2','3','4','5')),
    prise MONEY
);
CREATE TABLE guest(
    id SERIAL PRIMARY KEY,
    guest_name VARCHAR(20),
    guest_surname VARCHAR(20),
    passport_number   VARCHAR(20)
);

CREATE TABLE guest_Record
(
    room_number         SMALLINT,
    guest_id            INT,
    date_of_occupation  DATE,
    date_of_eviction    DATE,
    status VARCHAR(20) CHECK (status IN ('ACTIVE', 'FINISHED')),
    FOREIGN KEY (room_number) REFERENCES room(room_number),
    FOREIGN KEY (guest_id) REFERENCES guest(id)
);
CREATE TABLE service_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    prise MONEY
);
CREATE TABLE  room_service(
    room_number INT REFERENCES room,
    service_type_id INT REFERENCES service_type,
    PRIMARY KEY (room_number, service_type_id)
);