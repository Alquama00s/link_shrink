create database linkshrink;
create user backend;
create role application;
create schema linkshrink_sch authorization commandcenter;
grant usage, select, insert, update, delete on all tables in schema
linkshrink_sch to application;
grant connect on database linkshrink to application;
grant application to backend;
ALTER USER backend WITH PASSWORD 'pass_876';

-- grant select, insert, update, delete on all tables in schema
-- linkshrink_sch to backend;

-- grant usage on schema linkshrink_sch to backend;