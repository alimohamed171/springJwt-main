package com.helloIftekhar.springJwt.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "borrowed_books")
@Data
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Temporal(TemporalType.DATE)
    @Column(name = "borrow_date")
    private Date borrowDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "status")
    private String status; // Status of the borrowing relationship (e.g., "Approve", "reject")

    // Getters and setters
}
