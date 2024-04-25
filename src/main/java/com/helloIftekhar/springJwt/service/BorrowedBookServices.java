package com.helloIftekhar.springJwt.service;

import com.helloIftekhar.springJwt.model.Book;
import com.helloIftekhar.springJwt.model.BorrowedBook;
import com.helloIftekhar.springJwt.model.User;

import com.helloIftekhar.springJwt.repository.BookRepository;
import com.helloIftekhar.springJwt.repository.BorrowBookRepo;
import com.helloIftekhar.springJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookServices {

    @Autowired
    private BorrowBookRepo borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private int borrowBookId;

    // borrowBook
    public void borrowBook(int userId, int bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));


        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book is not available for borrowing because no available copies");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


            // Create a new BorrowedBook entity
            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBook(book);
            borrowedBook.setUser(user);
            borrowedBook.setBorrowDate(new Date());
            LocalDate currentDate = LocalDate.now();
            LocalDate returnDate = currentDate.plusWeeks(1);
            borrowedBook.setReturnDate(java.sql.Date.valueOf(returnDate));
            borrowedBook.setStatus("Borrowed");

            // Update book availability
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);


            userRepository.save(user);
            // Save the borrowing record to the database
            borrowedBookRepository.save(borrowedBook);

    }

    // get
    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBookRepository.findAll();
    }

    // return
    public void returnBorrowedBook(int userId, int bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(checkBorrowedBooksByBookId(bookId,userId) ){

                Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findById(borrowBookId);

                if(borrowedBook.isPresent()){
                    borrowedBook.get().setStatus("returned");
                    borrowedBook.get().setReturnDate(new Date());

                    // Update book availability
                    book.setAvailableCopies(book.getAvailableCopies() + 1);
                    bookRepository.save(book);

                    userRepository.save(user);
                }
            }
            else {
                throw new IllegalArgumentException("this user didn't borrow this book ");
            }
        }

    public Boolean checkBorrowedBooksByBookId(int bookId,int userId) {

        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        boolean hasBorrowedBooks = false;
        for (BorrowedBook borrowedBook : borrowedBookList) {
            if (borrowedBook.getBook().getBookID()== bookId && borrowedBook.getUser().getId()==userId && !borrowedBook.getStatus().equals("returned")) {
                hasBorrowedBooks = true;
                // id
                borrowBookId = borrowedBook.getId();
                return hasBorrowedBooks ;
            }
        }
        return hasBorrowedBooks;
    }


}

