# Student Online Forum
`Online Student Forum`<br>
The project of the course Software Engineering<br>
All the processes are following the instructions of __Agile development__
##  Operations of this code
This project is a Front-end and back-end separation one. All the front-end codes are in the Folder `"vue"`, and the back-end codes are in the Folder `"springboot"` <br>
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
##  Requirments
In recent years, many new students have been forced to live off-campus due to the lack of accommodation on campus, but new students are new to university and are unfamiliar with many things. We have observed this problem and decided to create a student forum to help new students get acquainted with the campus.
### Use case diagram
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/User%20case.jpg)
### Evolutionary Model
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/Evolutionary%20model.png)
### Functional Requirements
*   Register page
    1. Verify whether the user have registered.
    2. Verify whether username to have a special symbol.
    3. Verify username length greater than 3 less than 10.
    4. Provide register function.
*   Login Page
    1. Implement user login function.
    2. Verify whether the user exit.
*   Complaint Page
    1. Implement Complaint account function.
*   Modify Information Page.
    1. Implement modify information function.
    2. Upload the picture as an avatar.
*   Post Page.
    1. The user can create a new post in this page.
    2. The user can create a new comment in this page.
    3. The user can like the posts in this page.
    4. The user can stomp the posts in this page.
    5. The user can input the keyboard to search posts in this page.
    6. The user can select different sections in this page.
    7. The user can collect the posts in this page.
    8. The user can report the posts in this page.
    9. The poster can delete or edit the posts in this page.
    10.The commenter can delete the comment in this page.
    11.The poster can delete the comment under his own post in this page.
    12.The user can view the best post in this page.
    13.Users can view posts by when they were posted or how many times they were read.
*   System Announcement Page
    1. The administrator can create the new announcement in this page.
    2. The User can view the new system announcement in this page.
*   Personal Page
    1. Users can view their own posts or their comments and favorite posts on this page.
*   Manage Page
    1. The administrator can manage user, news, and posts in this page.
    2. 
##  Design
### Class diagram
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/class%20diagram.jpg)
### Sequence diagram
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/sequence%20diagram.png)

##  Development
1.  Task allocations:
    *   Chen Boyu   __Scrum Master__
    *   Liu  Yubo   __Product Owner__
    *   Wang Yuzhe  __Testing__      
    *   Bai  Enduo  __Testing__      
2. __Leagoo Documents__ 
3. __Demo__
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/index.jpg)

##  Testing
1.  Black-box testing
2.  White-box testing
3.  Automatic testing
4.  Unit testing
5.  Integration testing

##  Others
### Team
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/Team.jpg)
### Gantt charts
![image](https://github.com/Travis-Chen00/Student-Online-Forum/blob/main/Images/Gantt%20charts.png)

##  Conclusion
__Got A+ in this Course__.
