drop table if exists linkshrink_sch.role_map_user;
drop table if exists linkshrink_sch.role_map_client;
drop table if exists linkshrink_sch.clients;
drop table if exists linkshrink_sch.users;
drop table if exists linkshrink_sch.roles;


create table linkshrink_sch.users(
	id serial primary key,
	email varchar(100),
	name varchar(100),
	pwd varchar(200),
	is_active boolean not null default false,
	created_on timestamp not null default CURRENT_TIMESTAMP,
	updated_on timestamp,
	created_by int,
	updated_by int 
);

create table linkshrink_sch.clients(
	id serial primary key,
	client_id varchar(100),
	client_secret varchar(200),
	user_id int references linkshrink_sch.users(id),
	access_token_validity_sec int,
	refresh_token_validity_sec int,
	expires_on timestamp,
	is_active boolean not null default false,
	created_on timestamp not null default CURRENT_TIMESTAMP,
	updated_on timestamp,
	created_by int,
	updated_by int 
);

create table linkshrink_sch.roles(
	id serial primary key,
	name varchar(100),
    is_active boolean not null default false,
    created_on timestamp not null default CURRENT_TIMESTAMP,
    updated_on timestamp,
    created_by int,
    updated_by int
);

insert into linkshrink_sch.roles(name) values
('ROLE_USER'),
('ROLE_ADMIN'),
('ROLE_CLIENT');

create table linkshrink_sch.role_map_user(
	id serial primary key,
	user_id int references linkshrink_sch.users(id),
	role_id int references linkshrink_sch.roles(id),
	is_active boolean not null default false,
    created_on timestamp not null default CURRENT_TIMESTAMP,
    updated_on timestamp,
    created_by int,
    updated_by int
);

create table linkshrink_sch.role_map_client(
	id serial primary key,
	client_id int references linkshrink_sch.clients(id),
	role_id int references linkshrink_sch.roles(id),
    is_active boolean not null default false,
    created_on timestamp not null default CURRENT_TIMESTAMP,
    updated_on timestamp,
    created_by int,
    updated_by int
);

grant select, insert, update, delete on all tables in schema
linkshrink_sch to backend;

grant usage, select on all sequences in schema
linkshrink_sch to backend;

grant usage on schema linkshrink_sch to backend;
