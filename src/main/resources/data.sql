insert into flight (id, base, aircraft_name, equipment, registration_number) values (1, 'TXL', 'Boeing', '737', 'FL-0001');
insert into flight (id, base, aircraft_name, equipment, registration_number) values (2, 'MUC', 'Airbus', 'A321', 'FL-0002');
insert into flight (id, base, aircraft_name, equipment, registration_number) values (3, 'LHR', 'Boeing', '747-400', 'FL-0003');
insert into flight (id, base, aircraft_name, equipment, registration_number) values (4, 'HAM', 'Airbus', 'A320', 'FL-0004');


insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (1, 'TXL', 'MUC', '60', '10:00', 1);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (2, 'TXL', 'MUC', '60', '15:00', 1);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (3, 'TXL', 'MUC', '60', '16:00', 1);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (4, 'TXL', 'MUC', '60', '18:00', 1);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (5, 'TXL', 'MUC', '40', '21:00', 1);

insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (6, 'MUC', 'LHR', '120', '10:00', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (7, 'MUC', 'TXL', '60', '13:00', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (8, 'MUC', 'TXL', '60', '15:00', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (9, 'MUC', 'LHR', '120', '15:30', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (10, 'MUC', 'HAM', '60', '17:30', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (11, 'MUC', 'LHR', '150', '18:00', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (12, 'MUC', 'LHR', '120', '20:00', 2);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (13, 'MUC', 'TXL', '60', '22:00', 2);

insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (14, 'LHR', 'HAM', '150', '09:00', 3);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (15, 'LHR', 'TXL', '120', '12:00', 3);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (16, 'LHR', 'TXL', '120', '17:00', 3);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (17, 'LHR', 'MUC', '120', '20:30', 3);

insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (18, 'HAM', 'MUC', '60', '10:00', 4);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (19, 'HAM', 'MUC', '60', '13:00', 4);
insert into flight_schedule (id, origin, destination, flight_time, departure_time, flight_id) values (20, 'HAM', 'MUC', '60', '20:00', 4);

insert into airport_zone (id, airport_code, zone_name) values (1, 'TXL', 'EUROPE_AMSTERDAM');
insert into airport_zone (id, airport_code, zone_name) values (2, 'MUC', 'EUROPE_AMSTERDAM');
insert into airport_zone (id, airport_code, zone_name) values (3, 'LHR', 'EUROPE_LONDON');
insert into airport_zone (id, airport_code, zone_name) values (4, 'HAM', 'EUROPE_AMSTERDAM');

