Table sensor_fire {
  id integer [primary key]
  longitude integer
  latitude integer
  start_at datetime
  edit_at datetime
  level integer
}

Table fire_station {
  id integer [primary key]
  longitude integer
  latitude integer

}

Table fire_truck {
  id integer [primary key]
  fire_station_id integer [ref: > fire_station.id]
  fire_truck_type_id integer [ref: > fire_truck_type.id]
  longitude integer
  latitude integer
}

Table fire_fighter {
  id integer [primary key]
  firstname String
  surname String
  energy integer
  in_mission bool
  fire_station_id integer [ref: > fire_station.id]
  team_id integer [ref: > team.id]

}
Table fire_truck_type {
  id integer [primary key]
  type String
}

Table fire {
  id integer [primary key]
  is_finished bool

}
Table sensor_event {
  sensor_id integer [primary key, ref: > sensor_fire.id]
  fire_id integer [primary key, ref: > fire.id]
}
Table team {
  id integer [primary key]
  name String
}
Table intervention {
  id integer [primary key]
  team_id integer [primary key, ref: > team.id]
  fire_truck_id integer [primary key, ref: > fire_truck.id]
  fire_id integer [primary key, ref: > fire.id]
  taken_at datetime
  arrival_at datetime
}