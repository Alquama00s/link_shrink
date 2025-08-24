create role application;
create schema linkshrink_sch authorization application;
grant usage, select, insert, update, delete on all tables in schema
linkshrink_sch to application;
grant connect on database linkshrink to application;
grant application to backend;

-- grant select, insert, update, delete on all tables in schema
-- linkshrink_sch to backend;

-- grant usage on schema linkshrink_sch to backend;