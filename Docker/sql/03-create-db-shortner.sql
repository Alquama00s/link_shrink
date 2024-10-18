
drop table if exists linkshrink_sch.urls;
create table linkshrink_sch.urls (
	id serial primary key,
	short_url varchar(1000) not null unique,
	long_url varchar(2000) not null,
	generated boolean not null,
	expiry_after timestamp,
	is_active boolean,
	created_on timestamp not null default CURRENT_TIMESTAMP,
	updated_on timestamp,
	created_by int,
	updated_by int
);