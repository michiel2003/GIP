create table Image(
	id integer unsigned auto_increment primary key,
	imageURL varchar(255)
);

insert into image (imageURL) values
	("file://ionad1/users/Leerlingen/mdecap/My%20Documents/GIP/GIP2021/SampleImages/BlueSquare.png");
	
drop table image;