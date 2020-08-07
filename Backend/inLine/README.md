to set up MySQL on your machine:

Follow a Youtube tutorial on how to ge tthe MySQL server and workbench running on your specific machine (Mac, Windows)...

sign into MySQL via command line and then use command:

source directory-path-of-database-file.mysql

then follow the instructions on this link to give Spring access to the database. https://spring.io/guides/gs/accessing-data-mysql/

Make sure you set the username to "springuser" and password to "springpassword"!


Spring security:

jwt reader https://jwt.io

In postman after logging in or registering copy the token.
For each request, go to authorization select type "bearer token" and paste the token and then submit the request.