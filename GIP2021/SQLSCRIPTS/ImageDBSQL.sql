insert  into image (imageurl) values
("D:/GIP/GIP/GIP2021/SampleImages/BlueSquare.png");

insert  into image (imageurl) values
("tester");

insert into authors (author_name) values
("joske")

create table authors (author_id integer not null auto_increment, author_name varchar(255), email varchar(255), last_name varchar(255), phone varchar(255), primary key (author_id));
create table filepath (id integer not null auto_increment, path varchar(255), primary key (id));
create table icons (id integer not null auto_increment, iconurl varchar(255), primary key (id));
create table image (id integer not null auto_increment, imageurl varchar(255), authors_author_id integer, icons_icon_id integer, locations_location_id integer, primary key (id));
	create table imagetag (imageid integer not null, tagid integer not null);
create table locations (locationid integer not null auto_increment, location_name varchar(255), primary key (locationid));
create table tags (id integer not null auto_increment, tagname varchar(255), primary key (id));
alter table image add constraint FK2xuct3mig97wbv0vlkpd34ovi foreign key (authors_author_id) references authors (author_id);
alter table image add constraint FKmav237bxr5b1y7u2wn4gdpql8 foreign key (locations_location_id) references locations (locationid);
alter table imagetag add constraint FKqf48l3tkdklv0vngrui15w4rf foreign key (tagid) references tags (id);
alter table imagetag add constraint FKkdiyw8ygjwuj4hs53ge2qr74x foreign key (imageid) references image (id);
alter table image add constraint FKrtvtbtw8bmd7drxy71xswtjnv foreign key (icons_icon_id) references icons (id);

alter table authors add email varchar(255);
alter table authors add last_name varchar(255);

drop table image;
drop table imagetag;
drop table tags;
drop table authors;
drop table icons;
drop table imagetag;
drop table locations;
drop table filepath;

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
("E:/gip/GIP/GIP2021/SampleImages")

delete from filepath where id = 2;

delete from authors where author_id = 5;

select path from filepath;

select author_id, author_name, phone from authors;

select author_id, author_name, phone, email, last_name from authors;

insert into authors(author_name, last_name,email, phone) values ("michiel", "de cap", "michieldecap@gmail.com", "0498163191")

select * from authors where authors.author_name like "michiel" and authors.last_name like "de cap"

select iconurl from icons;

select path from filepath;

select * from icons where iconurl like "U:/GIP michiel 2021/GIP/GIP2021/DATA/ICONS/285A1fG5PCg.jpg";

select icons.id, icons.iconurl from image join icons ON image.icons_icon_id = icons.id where image.imageurl = "U:/GIP michiel 2021/GIP/GIP2021/SampleImages/2wqY7joWzIo.jpg";

select image.imageurl from icons join image on icons.id = image.icons_icon_id where icons.iconurl like "D:/GIP2021/GIP/GIP2021/DATA/ICONS/43jZxErIEtE.jpg";

select icons.id, icons.iconurl from image join icons on icons.id = image.icons_icon_id where image.imageurl like "U:/GIP michiel 2021/GIP/GIP2021/SampleImages/2wqY7joWzIo.jpg";

select icons.iconurl from image join icons ON image.icons_icon_id = icons.id where image.imageurl like "U:/GIP michiel 2021/GIP/GIP2021/SampleImages/2wqY7joWzIo.jpg";

select location_name from locations join image ON image.locations_location_id = locations.locationid where imageurl like 	;

select * from authors where authors.author_name like "michiel" and authors.last_name like "de cap" and phone like "phone" and email like "email";

select * from authors where author_id = 7;