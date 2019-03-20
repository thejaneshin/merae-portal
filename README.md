# Merae Portal
Merae Portal is a Spring Boot web application that assists [Merae](http://www.themerae.com/) employees with keeping track of users and their designated projects. Its front-end UI is built with HTML and CSS (Bootstrap), and Thymeleaf as the template engine. The application's user authentication and authorization are implemented using Spring Security. Merae Portal also utilizes Spring Data JPA and Hibernate to store and retrieve user and project information from the database.

![Image of landing screen for admins](https://i.imgur.com/gpED26A.png)

## Role Permissions
**Designer**
* Home
  - View all of their assigned, completed, and cancelled projects.
  - Edit status for assigned projects to keep track of progress.
  - Submit projects and Undo for completed projects.
* Settings
  - View account information in Profile page.
  - Edit contact information.
  - Change password.
  
**Admin**
* Includes all of the Designer's permissions.
* Employees Projects
  - View all user project tables regardless of status.
  - Add new projects to the database.
  - Assign projects to designers.
  - Update and Delete projects.
  - Undo completed or cancelled projects.
* Employees Information
  - View all user tables, separated by roles.
  - Add new employees with their corresponding roles.
  - Update designers' account information.
  - Reset designers' passwords using random generation.

## Database Configuration
Although MySQL script are provided in in [sql-scripts](https://github.com/thejaneshin/merae-portal/tree/master/meraeportal/sql-scripts) of this project, you may use any relational database with Merae Portal. Make sure to uncomment and edit the url, username, and password in [application.properties](https://github.com/thejaneshin/merae-portal/blob/master/meraeportal/src/main/resources/application.properties). For example:
```
spring.datasource.url=jdbc:mysql://localhost:3306/merae-database
spring.datasource.username=user
spring.datasource.password=pass
```

## Running Merae Portal in command line
```
git clone https://github.com/thejaneshin/merae-portal.git
cd merae-portal/meraeportal
mvnw spring-boot:run
```
You can then access the login screen for Merae Portal here: http://localhost:8080/

## Working with Merae Portal in Eclipse
The following items should be installed in your system:
* Maven (https://maven.apache.org/)
* git command line tool (https://help.github.com/articles/set-up-git)
* Eclipse with m2e plugin (http://eclipse.org/m2e/download/)

### Steps:
1. In the command line
```
git clone https://github.com/thejaneshin/merae-portal.git
```
2. Inside Eclipse
```
File -> Import -> Maven -> Existing Maven projects
```
