insert  into image (imageurl) values
("D:/GIP/GIP/GIP2021/SampleImages/BlueSquare.png");

insert  into image (imageurl) values
("tester");

insert into authors (author_name) values
("joske")

create table authors (author_id integer not null auto_increment, author_name varchar(255), primary key (author_id));
create table locations (locationid integer not null auto_increment, location_name varchar(255), primary key (locationid));
create table image (id integer not null auto_increment, imageurl varchar(255), authors_author_id integer, locations_location_id integer, primary key (id));
create table tags (id integer not null auto_increment, tagname varchar(255), primary key (id));
create table filepath (id integer not null auto_increment, path varchar(255), primary key (id));
create table imagetag (imageid integer not null, tagid integer not null);

alter table image add constraint FK2xuct3mig97wbv0vlkpd34ovi foreign key (authors_author_id) references authors (author_id);
alter table image add constraint FKmav237bxr5b1y7u2wn4gdpql8 foreign key (locations_location_id) references locations (locationid)
alter table imagetag add constraint FKqf48l3tkdklv0vngrui15w4rf foreign key (tagid) references tags (id);
alter table imagetag add constraint FKkdiyw8ygjwuj4hs53ge2qr74x foreign key (imageid) references image (id);

drop table image;
drop table imagetag;
drop table tags;
drop table authors;

select * from imagetag;
select imageid, tagid, image.id as "imgid", image.imageurl, tags.id, tags.tagname from imagetag join image on imagetag.imageid = image.id join tags on tags.id = imagetag.tagid where image.imageurl like "tester";


select distinct image.imageurl from imagetag join tags on imagetag.tagid = tags.id join image on imagetag.imageid = image.id;


select * from authors where author_name like "joske" limit 1;

select author_name from authors join image on author_id = image.authors_author_id where image.imageurl like "joske"

select author_name from authors join image on author_id = image.authors_author_id where image.imageurl like "U:/GIP michiel 2021/GIP/GIP2021/SampleImages/BlueSquare.png" limit 1

select distinct image.imageurl from authors join image on authors.author_id = image.authors_author_id where authors.author_name like "Joske";

select distinct image.imageurl from authors join image on authors.author_id = image.authors_author_id where authors.author_name like "Joske";

select * from locations;

insert into locations (location_name) values ("Mol");

select * from locations where location_name like "mol"

select distinct image.id, image.imageurl, image.authors_author_id, image.locations_location_id from imagetag join tags on imagetag.tagid = tags.id join image on imagetag.imageid = image.id where tags.tagname like "lokok"

select * from tags where tagname like "fff"

delete from tags

insert into filepath (path) values
("D:/GIP/GIP/GIP2021/SampleImages")

delete from filepath where id = 2;	

select path from filepath;