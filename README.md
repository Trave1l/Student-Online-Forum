# Student Online Forum
`Online Student Forum`<br>
The project of the course Software Engineering<br>
##  Operations of this code
This project is a Front-end and back-end separation one. All the front-end codes are in the Folder "vue", and the back-end codes are in the Folder "springboot" <br>
1.  Language:   Java
2.  Frame:      Springboot & Vue3
### Operations:
1.  Download the .sql file and all the code
2.  In the Folder `springboot\src\main\resources` modify the file `application.properties` <br>
    ```java
    server.port=9092

    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=GMT%2B8  
    //change community to your database name
    
    spring.datasource.username=root     //change to your username for database
    spring.datasource.password=123456   //change to your password for database
    ```
