INSERT INTO room VALUES (1,'SERVICED','STANDARD',2, 2, 100),
                        (2,'SERVICED','VIP',1, 5, 200),
                        (3,'REPAIRABLE','STANDARD', 4, 2, 500),
                        (7,'SERVICED','STANDARD', 3, 2, 200),
                        (4,'SERVICED', 'STANDARD',2,4,350);
INSERT INTO guest (guest_name,guest_surname,passport_number) VALUES
                        ( 'Nekuz','Nazirjonov','A34311'),
                        ('Sasha','Nikitin','N12412'),
                        ('Andrew','Abish','G4253'),
                        ('Albert','Begin','T3213'),
                        ('Boris','Petrov','P34211');
INSERT INTO guest_record (room_number, guest_id, date_of_occupation, date_of_eviction, status, lived_in_room)
VALUES
    (1, 1, '2024-01-11', '2025-01-28', 'ACTIVE', 1),
    (1, 4, '2024-01-11', '2025-01-28', 'ACTIVE',1),
    (2, 2, '2024-12-28', '2024-01-24', 'ACTIVE',2),
    (3, 3, '2024-12-12', '2024-01-12', 'ACTIVE',3),
    (7, 5, '2025-01-05', '2025-01-06', 'FINISHED',7);

INSERT INTO service_type(name, price) VALUES
                        ('BREAKFAST',30),
                        ('LYNCH',25),
                        ('DINNER',50),
                        ('CLEANING',20),
                        ('SPA',45);

INSERT INTO room_service VALUES (1,1),
                                (1,2),
                                (2,3),
                                (2,2),
                                (3,2),
                                (3,1),
                                (3,5),
                                (3,4);

-- SELECT room.room_number from room WHERE room_number =?
DELETE FROM guest_record WHERE guest_id = 7;
insert into guest_record(room_number, guest_id, date_of_occupation, date_of_eviction, status) values
                                                                                                  (1, 1, '2024-01-11', '2025-01-28', 'ACTIVE')