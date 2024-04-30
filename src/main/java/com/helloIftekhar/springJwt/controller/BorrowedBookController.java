package com.helloIftekhar.springJwt.controller;


import com.helloIftekhar.springJwt.service.BorrowedBookServices;
import com.helloIftekhar.springJwt.model.BorrowedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class BorrowedBookController {


    @Autowired
    private BorrowedBookServices borrowedBookService;

    @PostMapping("/user_only/borrow-book")
    public ResponseEntity<String> borrowBook(
            @RequestParam int userId,
            @RequestParam int bookId
    ) {
        try {
            borrowedBookService.borrowBook(userId, bookId);
            return ResponseEntity.ok("Book borrowed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to borrow book: " + e.getMessage());
        }
    }



    //get all books ()
    @GetMapping("/user_only/getBorrowedBooks")
    public List<BorrowedBook> getBorrowedBooks(){
        List<BorrowedBook> bookList = borrowedBookService.getBorrowedBooks();
        return  bookList;
    }
    //get all books ()
//    @GetMapping("/user_only/getBorrowedBooks")
//    public List<BorrowedBook> getBorrowedBooks(){
//        List<BorrowedBook> bookList = borrowedBookService.getBorrowedBooks();
//
//        for (BorrowedBook book : bookList) {
//            book.getBook().setISBN(AES.tripleDESDecrypt(book.getBook().getISBN()));
//            book.getBook().setImageLink(AES.tripleDESDecrypt(book.getBook().getImageLink()));
//            book.getBook().setAuthor(AES.tripleDESDecrypt(book.getBook().getAuthor()));
//        }
//        return  bookList;
//    }
    @PutMapping("/user_only/returnBorrowedBook/{userId}/{bookId}")
    public ResponseEntity<?>returnBorrowedBook(@PathVariable int userId,@PathVariable int bookId){
        try {
            borrowedBookService.returnBorrowedBook(userId, bookId);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return book: " + e.getMessage());
        }
    }



}


