# Image database

## how to install

**It is recommeneded to use following programs:**

| Downloads | 
| ----------- |
| [DBeaver](https://dbeaver.io/download/) |
| [Eclipse](https://www.eclipse.org/downloads/packages/ ) |
| [XAMPP](https://www.apachefriends.org/index.html) |

If you already have the application downloaded skip to **installation process**

---
**project download options**
* Clone the repository to an empty folder
	```
	git clone https://github.com/michiel2003/GIP.git
	```
* Or [download](https://github.com/michiel2003/GIP/archive/refs/heads/main.zip) it as a zip file

---

**installation process**

1. Open DBeaver and execute the [ImageDBDatabaseIntialiser.sql file](GIP2021/SQLSCRIPTS/ImageDBDatabaseIntialiser.sql) as an sql script on a database of your choice
2. Open Eclipse and import the project as an existing maven project
3. Alter line 4, 8, 11 in the [application.properties file](GIP2021/src/main/resources/application.properties) if needed to match your database
4. (optional) Add a folder from where the application will take all images in set folder and auto insert them into the program 

	**!!! ONLY USE FORWARD SLASH NO BACKSLASH**
	```
	insert into filepath (path) values ("{filepath to your folder}")
	```
5. In eclipse run the project as a Java Application (alt + shift + x, j)
6. Open [index file](GIP2021/Web/index.html)

if you followed all the steps above correctly the program should be fully functioning

refer to [manual.docx](MANUAL.docx) for info on how to use the web interface
