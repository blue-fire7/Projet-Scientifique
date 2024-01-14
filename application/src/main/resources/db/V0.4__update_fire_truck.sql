alter table fire_truck
    alter column longitude type numeric(9, 6) using longitude::numeric(9, 6);

alter table fire_truck
    alter column latitude type numeric(9, 6) using latitude::numeric(9, 6);
