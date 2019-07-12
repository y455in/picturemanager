System requirements:
Java Jdk 1.8 
Maven 3.3.3+

Execution:
To execute via maven run the following commands from command line
	mvn package
	mvn jetty:run

The application will bbe reachable via:
http://localhost:8080/picturebook


Valid endpoints:

Album management:
Get all albums:		GET	/albums
Get all pictures in an album:		GET	/albums/{albumId}/pictures
Get album:		GET	/albums/{albumId}
Get picture:		GET	/albums/{albumId}/pictures/{pictureId}
Create Album: 	POST	/albums
Create Picture: 	POST	/albums/{albumId}/pictures
Update Album:		PUT	/albums/{albumId}
Update Picture:		PUT	/albums/{albumId}/pictures/{pictureId}
Delete Album:		DELETE	/albums/{albumId}
Delete Picture:		DELETE	/albums/{albumId}/pictures/{pictureId}
