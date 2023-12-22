drop table if exists sensor;
drop table if exists fire;
drop table if exists sensor_event;
drop table if exists team;
drop table if exists fire_station;
drop table if exists fire_truck;
drop table if exists fire_truck_type;
create table sensor(
  id serial primary key,
  longitude decimal(9, 6) not null,
  latitude decimal(9, 6) not null
);
create table fire (
  id serial primary key,
  started_at timestamp not null,
  ended_at timestamp
);
create table sensor_event(
  sensor_id bigint references sensor(id),
  fire_id bigint references fire(id),
  update_at timestamp,
  level integer not null,
  constraint sensor_event_pkey primary key(sensor_id, fire_id, update_at)
);
create table fire_station(
  id serial primary key,
  longitude decimal(9, 6) not null,
  latitude decimal(9, 6) not null
);
create table team(
  id serial primary key,
  name varchar(50) not null
);
create table fire_truck_type(
  id serial primary key,
  type varchar(50) not null,
  speed integer not null
);
create table fire_truck(
  id serial primary key,
  fire_station_id bigint references fire_station(id) not null,
  fire_truck_type_id bigint references fire_truck_type(id) not null,
  longitude decimal(9, 6) not null,
  latitude decimal(9, 6) not null
);
create table intervention(
  team_id bigint references team(id) not null,
  fire_truck_id bigint references fire_truck(id) not null,
  fire_id bigint references fire(id) not null,
  path path,
  constraint intervention_pkey primary key(team_id, fire_truck_id, fire_id)
);
create table fire_fighter(
  id serial primary key,
  firstname varchar(50) not null,
  surname varchar(50) not null,
  energy integer default 10,
  fire_station_id bigint references fire_station(id) not null,
  team_id bigint references team(id) not null
);