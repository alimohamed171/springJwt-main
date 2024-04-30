package com.helloIftekhar.springJwt.controller;


import com.helloIftekhar.springJwt.DES;
import com.helloIftekhar.springJwt.model.Book;
import com.helloIftekhar.springJwt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {


    @Autowired
    private BookService bookService;


    @PostMapping("/admin_only/addBook")
    public ResponseEntity<?> addBook(
            @RequestBody Book book
    ) {


        if(book.getTitle().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("book title is empty.");
        }if(book.getAuthor().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("book author is empty.");
        }if(book.getISBN().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("book ISBN is empty.");
        }

        if (!bookService.isTitleAvailable(book.getTitle())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("book title already exists.");
        }

        String isbn = DES.tripleDESEncrypt(book.getISBN());
        if (!bookService.isISBNAvailable(isbn)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("isbn already exists.");
        }
        bookService.addBook(encrypt(book));

        return ResponseEntity.ok(book);
    }


    @RequestMapping("/admin_only/deleteBook/{id}")
    public ResponseEntity<String> deleteBookById(
            @PathVariable int id
    ) {
        if (bookService.bookIsExist(id)) {
            if(bookService.checkBorrowedBooksByBookId(id)){
                return ResponseEntity.ok("this book is already borrowed");
            }
            else{
                bookService.deleteById(id);
                return ResponseEntity.ok("Book with ID " + id + " deleted successfully");

            }

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("book with provided id does not exist");

    }
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(){

        List<Book> bookList = bookService.getAllBooks();

        for (Book book : bookList) {
          book.setISBN(DES.tripleDESDecrypt(book.getISBN()));
          book.setImageLink(DES.tripleDESDecrypt(book.getImageLink()));
          book.setAuthor(DES.tripleDESDecrypt(book.getAuthor()));
          book.setRackNumber(book.getRackNumber());
        }

        return bookList;
    }

    @PutMapping("/admin_only/updateBook/{id}")
    public ResponseEntity<?>updateBook(
            @PathVariable int id,
            @RequestBody Book bookDetails
    ){
        Optional<?> updatedBook = bookService.updateBook(id,encrypt(bookDetails));
        return ResponseEntity.ok(updatedBook);

    }

    private Book encrypt(Book book){
        Book encreptedBook = book;
        encreptedBook.setISBN(DES.tripleDESEncrypt(book.getISBN()));
        encreptedBook.setImageLink(DES.tripleDESEncrypt(book.getImageLink()));
        encreptedBook.setAuthor(DES.tripleDESEncrypt(book.getAuthor()));
        encreptedBook.setRackNumber(book.getRackNumber());
        return encreptedBook;
    }


}
