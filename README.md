
# MyShowConductor System 🎞️🔖

- MyShowConductor System is backend Spring Boot project.
- It's movie tickets booking system
- Its consisting different layers like Controllers, Services, Repositories, DTO(Data Transfer Object)
- These layers in the project increases its code readability, maintainability, understandability because different logic is written in diffrent layers.
-  Implemented email notifications for ticket booking and cancellation.
<!-- - Implemented email notifications for ticket booking and cancellation to keep user informed about their transactions. -->


### By using this system user and admin can perform following operations...

#### Admin :
- Add Theatres, Movies, Shows
- Update Theatres, Movies, Shows
- Delete Theatres, Movies, Shows
- And various other operations(APIs)

#### User :
- Search Theatres, Movies, Shows
- Book Tickets
- Cancel Tickets
- can filter data 
- And various other operations(APIs)


### Tech-Stack used :
- Java
- Spring Boot
- MySQL
- JPA
- Hibernate
- Maven
- IntelliJ
- Postman 

### Models / Entities used in Project :
- User
- Movie
- Theatre
- Theatre_Seat
- Show
- Show_Seat
- Ticket

### MySql Schema(Tables Design) :
![MyShowConductor_Schema_1](https://github.com/viju-dev/MyShowConductor_System/assets/71461702/380157eb-cac7-4d76-b43c-fdb6ec9312c1)



###  APIs :
<!-- #### Movie APIs :
- Add a Movie
- Get Movie by ID
- Get Movie by Name
- Get Movies by Languages
- Get Movies by Genre
- Get Movies by Format
- Get All Movies
- Get Top Movie
- Get Movie with Maximum Shows
- Get Collection by Movie
- Edit Movie
- Delete Movie by ID
- Delete Movie by Name 
-->



    Movie APIs -
        Add a Movie
        Get Movie by ID
        Get Movie by Name
        Get Movies by Languages
        Get Movies by Genre
        Get Movies by Format
        Get All Movies
        Get Top Movie
        Get Movie with Maximum Shows
        Get Collection by Movie
        Edit Movie
        Delete Movie by ID
        Delete Movie by Name

    Theatre APIs -
        Add a Theatre
        Get Theatres with Shows by Location and Movie
        Get All Theatres by Location
        Get All Theatres
        Get All Theatres by Movie
        Delete Theatre by ID

    Ticket APIs -
        Add a Ticket
        Get All Tickets by User
        Delete Ticket by ID
        Send Email for Ticket

    User APIs -
        Add a User
        Update User Location by Mobile Number
        Get All Users
        Delete User by ID


### Ticket Booking mail sent by system
![Screenshot (10903)](https://github.com/viju-dev/MyShowConductor_System/assets/71461702/d31b635d-7d2d-445f-81b1-b1b49612ed23)

### Ticket cancellation mail sent by system
![Screenshot (10909)](https://github.com/viju-dev/MyShowConductor_System/assets/71461702/51c48587-9376-44b3-9cc0-394e2697f58b)


