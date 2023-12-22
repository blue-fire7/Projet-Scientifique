create table event_publication
(
    completion_date  timestamp(6) with time zone,
    publication_date timestamp(6) with time zone,
    id               uuid not null
        primary key,
    event_type       varchar(255),
    listener_id      varchar(255),
    serialized_event varchar(255)
);

alter table event_publication
    owner to postgres;

