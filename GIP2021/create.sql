create table authors (author_id integer not null auto_increment, author_name varchar(255), primary key (author_id)) type=MyISAM
create table image (id integer not null auto_increment, imageurl varchar(255), authors_author_id integer, locations_location_id integer, primary key (id)) type=MyISAM
create table imagetag (imageid integer not null, tagid integer not null) type=MyISAM
create table locations (locationid integer not null auto_increment, location_name varchar(255), primary key (locationid)) type=MyISAM
create table tags (id integer not null auto_increment, tagname varchar(255), primary key (id)) type=MyISAM
alter table image add constraint FK2xuct3mig97wbv0vlkpd34ovi foreign key (authors_author_id) references authors (author_id)
alter table image add constraint FKmav237bxr5b1y7u2wn4gdpql8 foreign key (locations_location_id) references locations (locationid)
alter table imagetag add constraint FKqf48l3tkdklv0vngrui15w4rf foreign key (tagid) references tags (id)
alter table imagetag add constraint FKkdiyw8ygjwuj4hs53ge2qr74x foreign key (imageid) references image (id)
create table authors (author_id integer not null auto_increment, author_name varchar(255), primary key (author_id)) type=MyISAM
create table image (id integer not null auto_increment, imageurl varchar(255), authors_author_id integer, locations_location_id integer, primary key (id)) type=MyISAM
create table imagetag (imageid integer not null, tagid integer not null) type=MyISAM
create table locations (locationid integer not null auto_increment, location_name varchar(255), primary key (locationid)) type=MyISAM
create table tags (id integer not null auto_increment, tagname varchar(255), primary key (id)) type=MyISAM
alter table image add constraint FK2xuct3mig97wbv0vlkpd34ovi foreign key (authors_author_id) references authors (author_id)
alter table image add constraint FKmav237bxr5b1y7u2wn4gdpql8 foreign key (locations_location_id) references locations (locationid)
alter table imagetag add constraint FKqf48l3tkdklv0vngrui15w4rf foreign key (tagid) references tags (id)
alter table imagetag add constraint FKkdiyw8ygjwuj4hs53ge2qr74x foreign key (imageid) references image (id)
