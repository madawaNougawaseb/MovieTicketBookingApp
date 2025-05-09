
CREATE TABLE Users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    user_type VARCHAR(20) CHECK (user_type IN ('customer', 'admin')) NOT NULL
);


CREATE TABLE Movies (
    movie_id INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    rating DECIMAL(2,1),
    description TEXT,
    release_date DATE NOT NULL,
    poster_url VARCHAR(255)
);

CREATE TABLE Theatres (
    theatre_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);



CREATE TABLE Screens (
    screen_id INT PRIMARY KEY IDENTITY(1,1),
    theatre_id INT,
    screen_name VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL,
    FOREIGN KEY (theatre_id) REFERENCES Theatres(theatre_id)
);



CREATE TABLE Showtimes (
    showtime_id INT PRIMARY KEY IDENTITY(1,1),
    movie_id INT,
    screen_id INT,
    start_time DATETIME NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id),
    FOREIGN KEY (screen_id) REFERENCES Screens(screen_id)
);


CREATE TABLE Seats (
    seat_id INT PRIMARY KEY IDENTITY(1,1),
    screen_id INT,
    seat_number VARCHAR(10) NOT NULL,
    is_vip BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (screen_id) REFERENCES Screens(screen_id)
);



CREATE TABLE Bookings (
    booking_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    showtime_id INT,
    seat_id INT,
    status VARCHAR(20) CHECK (status IN ('pending', 'confirmed', 'cancelled')) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (showtime_id) REFERENCES Showtimes(showtime_id),
    FOREIGN KEY (seat_id) REFERENCES Seats(seat_id)
);



CREATE TABLE Payments (
    payment_id INT PRIMARY KEY IDENTITY(1,1),
    booking_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATETIME NOT NULL,
    payment_status VARCHAR(20) CHECK (payment_status IN ('pending', 'completed', 'failed')) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id)
);



CREATE TABLE Tickets (
    ticket_id INT PRIMARY KEY IDENTITY(1,1),
    booking_id INT,
    seat_id INT,
    price DECIMAL(10,2) NOT NULL,
    issued_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id),
    FOREIGN KEY (seat_id) REFERENCES Seats(seat_id)
);


 
-- Stored Procedures 

CREATE OR ALTER PROCEDURE BookTicket
@user_id INT,
@showtime_id INT,
@seat_id INT,
@amount DECIMAL(10,2)
AS
BEGIN
    DECLARE @seat_status INT;

    SELECT @seat_status = COUNT(*) FROM Bookings WHERE seat_id = @seat_id AND showtime_id = @showtime_id AND status = 'confirmed';

    IF @seat_status = 0
    BEGIN

        INSERT INTO Bookings (user_id, showtime_id, seat_id, status)
        VALUES (@user_id, @showtime_id, @seat_id, 'confirmed');

        DECLARE @booking_id INT;
        SET @booking_id = SCOPE_IDENTITY();

        INSERT INTO Payments (booking_id, amount, payment_date, payment_status)
        VALUES (@booking_id, @amount, GETDATE(), 'pending');

        INSERT INTO Tickets (booking_id, seat_id, price, issued_at)
        VALUES (@booking_id, @seat_id, @amount, GETDATE());
    END
    ELSE
    BEGIN
        PRINT 'Seat is already booked!';
    END
END;
GO


CREATE OR ALTER PROCEDURE CancelBooking
@booking_id INT
AS
BEGIN
    UPDATE Bookings SET status = 'cancelled' WHERE booking_id = @booking_id;
    UPDATE Payments SET payment_status = 'failed' WHERE booking_id = @booking_id;
END;
GO

CREATE OR ALTER PROCEDURE GetAvailableSeats
@showtime_id INT
AS
BEGIN
    SELECT s.seat_id, s.seat_number, s.is_vip
    FROM Seats s
    LEFT JOIN Bookings b ON s.seat_id = b.seat_id AND b.showtime_id = @showtime_id AND b.status = 'confirmed'
    WHERE b.seat_id IS NULL;
END;
GO

-- Triggers 


CREATE OR ALTER TRIGGER PreventOverbooking
ON Bookings
AFTER INSERT
AS
BEGIN
    IF EXISTS (
        SELECT seat_id FROM Bookings
        GROUP BY seat_id, showtime_id
        HAVING COUNT(*) > 1
    )
    BEGIN
        PRINT 'Error: This seat is already booked for the selected showtime!';
        ROLLBACK TRANSACTION;
    END
END;
GO


CREATE OR ALTER TRIGGER AutoCompletePayment
ON Bookings
AFTER UPDATE
AS
BEGIN
    IF UPDATE(status)
    BEGIN
        UPDATE Payments
        SET payment_status = 'completed'
        WHERE booking_id IN (SELECT booking_id FROM inserted WHERE status = 'confirmed');
    END
END;
GO


CREATE TABLE TicketLogs (
    log_id INT PRIMARY KEY IDENTITY(1,1),
    ticket_id INT,
    booking_id INT,
    issued_at DATETIME,
    FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id),
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id)
);
GO

CREATE TRIGGER LogTicketPurchase
ON Tickets
AFTER INSERT
AS
BEGIN
    INSERT INTO TicketLogs (ticket_id, booking_id, issued_at)
    SELECT ticket_id, booking_id, issued_at FROM inserted;
END;
GO



CREATE OR ALTER PROCEDURE RegisterUser
@name VARCHAR(100),
@email VARCHAR(255),
@password VARCHAR(255),
@phone VARCHAR(20),
@user_type VARCHAR(20)
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Users WHERE email = @email)
    BEGIN
        PRINT 'Email already exists!';
        RETURN;
    END
    INSERT INTO Users (name, email, password, phone, user_type)
    VALUES (@name, @email, @password, @phone, @user_type);
END;
GO


CREATE OR ALTER PROCEDURE ProcessPayment
@payment_id INT
AS
BEGIN
    UPDATE Payments SET payment_status = 'completed' WHERE payment_id = @payment_id;
    UPDATE Bookings SET status = 'confirmed' WHERE booking_id = (SELECT booking_id FROM Payments WHERE payment_id = @payment_id);
END;
GO

CREATE OR ALTER PROCEDURE GetUpcomingShowtimes
AS
BEGIN
    SELECT s.showtime_id, m.title, s.start_time, t.name AS theatre_name
    FROM Showtimes s
    JOIN Movies m ON s.movie_id = m.movie_id
    JOIN Screens scr ON s.screen_id = scr.screen_id
    JOIN Theatres t ON scr.theatre_id = t.theatre_id
    WHERE s.start_time >= GETDATE() AND s.start_time <= DATEADD(DAY, 7, GETDATE());
END;
GO

CREATE OR ALTER TRIGGER ValidatePaymentAmount
ON Payments
INSTEAD OF INSERT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM inserted WHERE amount <= 0)
    BEGIN
        PRINT 'Error: Payment amount must be greater than zero!';
    END
    ELSE
    BEGIN
        INSERT INTO Payments (booking_id, amount, payment_date, payment_status)
        SELECT booking_id, amount, payment_date, payment_status FROM inserted;
    END
END;
GO

CREATE TABLE BookingCancellationLogs (
    log_id INT PRIMARY KEY IDENTITY(1,1),
    booking_id INT,
    cancelled_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id)
);
GO

CREATE OR ALTER TRIGGER LogBookingCancellation
ON Bookings
AFTER UPDATE
AS
BEGIN
    IF EXISTS (SELECT 1 FROM inserted WHERE status = 'cancelled')
    BEGIN
        INSERT INTO BookingCancellationLogs (booking_id)
        SELECT booking_id FROM inserted WHERE status = 'cancelled';
    END
END;
GO


