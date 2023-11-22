
drop table if exists linkshrink_sch.urls;
create table linkshrink_sch.urls (
	id serial primary key,
	short_url varchar(7) not null unique,
	long_url varchar(2000) not null,
	created_on timestamp not null default CURRENT_TIMESTAMP,
	expiry_after timestamp
	
);

grant select, insert, update, delete on all tables in schema
linkshrink_sch to backend;

grant usage on schema linkshrink_sch to backend;