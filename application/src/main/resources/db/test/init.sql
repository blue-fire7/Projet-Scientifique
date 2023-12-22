INSERT INTO fire_station (latitude, longitude) VALUES (45.7779068, 4.8845135);
INSERT INTO fire_station (latitude, longitude) VALUES (45.7723269, 4.8483747);

INSERT INTO team (name) VALUES ('Team 1');
INSERT INTO team (name) VALUES ('Team 2');
INSERT INTO team (name) VALUES ('Team 3');

INSERT INTO fire_fighter (firstname, surname, fire_station_id, team_id) VALUES
                                                                            ('John', 'Doe', 1, 1),
                                                                            ('Jane', 'Doe', 1, 1),
                                                                            ('Alex', 'Doe', 1, 1),
                                                                            ('John', 'Smith', 1, 2),
                                                                            ('Jane', 'Smith', 1, 2),
                                                                            ('John', 'Doe', 2, 3),
                                                                            ('Jane', 'Doe', 2, 3);


INSERT INTO fire_truck_type (type, speed) VALUES ('Camion', 50);
INSERT INTO fire_truck_type (type, speed) VALUES ('El rapido', 80);

INSERT INTO fire_truck (fire_station_id, fire_truck_type_id, longitude, latitude) VALUES (1, 1, 45.7779068, 4.8845135);
INSERT INTO fire_truck (fire_station_id, fire_truck_type_id, longitude, latitude) VALUES (1, 2, 45.7779068, 4.8845135);
INSERT INTO fire_truck (fire_station_id, fire_truck_type_id, longitude, latitude) VALUES (2, 1, 45.7779068, 4.8845135);

INSERT INTO fire (started_at, ended_at) VALUES (now(), now());

INSERT INTO intervention (team_id, fire_truck_id, fire_id) VALUES (1, 1, 1);


