INSERT INTO room VALUES (1,'SERVICED',100,'STANDARD',2,2),
                        (2,'SERVICED',200,'VIP',1,5),
                        (3,'REPAIRABLE',500,'STANDARD',4,2),
                        (7,'SERVICED',200,'STANDARD',3,2);

INSERT INTO guest (guest_name,guest_surname,passport_number) VALUES
                        ( 'Nekuz','Nazirjonov','A34311'),
                        ('Sasha','Nikitin','N12412'),
                        ('Andrew','Abish','G4253'),
                        ('Albert','Begin','T3213'),
                        ('Boris','Petrov','P34211');
INSERT INTO guest_record (room_number, guest_id, date_of_occupation, date_of_eviction, status) VALUES
                         (1, 1, '2024-01-11', '2025-01-28', 'ACTIVE'),
                         (1,4,'2024-01-11', '2025-01-28', 'ACTIVE'),
                         (2, 2, '2024-12-28', '2024-01-24', 'ACTIVE'),
                         (3, 3, '2024-12-12', '2024-01-12', 'ACTIVE'),
                         (7, 5, '2025-01-05', '2025-01-06', 'FINISHED');
INSERT INTO service_type(name, prise) VALUES
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

