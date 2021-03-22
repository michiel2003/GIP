insert  into image (imageurl) values
("tester");

create table image (id integer not null auto_increment, imageurl varchar(255), primary key (id));
create table imagetag (imageid integer not null, tagid integer not null);
create table tags (id integer not null auto_increment, tagname varchar(255), primary key (id));
alter table imagetag add constraint FKqf48l3tkdklv0vngrui15w4rf foreign key (tagid) references tags (id);
alter table imagetag add constraint FKkdiyw8ygjwuj4hs53ge2qr74x foreign key (imageid) references image (id);


drop table image;
drop table imagetag;
drop table tags;

select * from imagetag;
select * from imagetag join image on imagetag.imageid = image.id join tags on tags.id = imagetag.tagid where image.imageurl like "tester";