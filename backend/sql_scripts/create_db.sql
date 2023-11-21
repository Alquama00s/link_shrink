create table linkshrink_sch.urls (
	id integer not null,
	short_url varchar(7) not null unique,
	long_url varchar(2000) not null,
	created_on timestamp not null default CURRENT_TIMESTAMP,
	expiry_after interval,
	
	primary key(id)
);