CineHub is a movie ticket booking application for a single theatre. It is built using Spring Boot and MySQL and provides APIs for user management , show scheduling and seat booking and booking cancellation.

The application focuses on separate functionalities for user and admin through dedicated modules and APIs. Users can view scheduled movies, shows and book tickets. Admin can manage movie and show scheduling.

A key feature of the application is concurreny-safe booking workflow. Transaction management and Seat-locking mechanisms are used to prevent double-booking and conflicts when multiple users attempt to book seats.Seats are temporarily locked during the booking process and they will be released after some amount of time in case of failures.

This project demonstrates backend development concepts including REST APIs , Spring Data JPA , Exception Handling , Transaction Management , Scheduling and Database relationship management.
