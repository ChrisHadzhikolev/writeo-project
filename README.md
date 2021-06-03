Writeo - Web App

IMPORTANT! 
    - The frontend is in src/main/resources/templates/my-app
    - The backend is in the root folder
    - The database is hosted through docker
    - The documentation is in the folder Documents

The project is written with Spring Boot(link: localhost:8080) and React(link: localhost:3000). The database is on localhost(a folder with it's name will be uploaded to the repository). I am using xampp to host it. The backend has full CRUD Operations for every Entity. The connection to the react project for now ensures adding, removing and getting articles. There is a new CI script, unit tests for 2 of the controllers and all the models, update feature for articles in react and design updates. Also the database was changed as well as the design document.

Features:
    - User Profiles with Authentication and Authorization with JJWT(Java JSON Web Token - https://javadoc.io/doc/io.jsonwebtoken/jjwt-api/latest/index.html) (Authors and Admins)
    - CRUD operations Articles(only accessible with Author Profile)
    - My Profile info
    - Admin Tables(with Buyers, Sells, Admins and Authors), They contain the important info
    - View without need to log in for buyers to purchase Articles that were made available by Authors(The card information is saved or used in any way, because it should be much more secure and legitimate business)
