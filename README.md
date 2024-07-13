
# SM App

## Description
SM App is a Spring Boot application designed to provide a comprehensive solution for user management, including features such as user registration, authentication, and email activation. Built with Java 17 and leveraging the Spring Boot framework, it aims to deliver a robust and scalable platform for developers.

## Features
- User Registration: Allows new users to sign up.
- Email Activation: It sends emails to users via RabbitMQ to make registration and activation processes asynchronous.
- Authentication: Supports login functionality with JWT token-based authentication.
- User Management: Enables CRUD operations on user data.


## DB Schema
![alt text](http://github.com/cndgdvrn/simple-social-media-app-with-spring-boot/blob/main/sm-app/docs/smapp-db-diagram.png?raw=true)

### Installation
1. Clone the repository 
   ```sh
   git clone https://github.com/cndgdvrn/simple-social-media-app-with-spring-boot.git
   ```
2. Execute the following command in the root directory before running the application to start the postgres database and rabbitmq server
      ```sh
   docker-compose -f docker-compose.yml up
   ```
3. Run the application

  
