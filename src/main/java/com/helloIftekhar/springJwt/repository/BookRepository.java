package com.helloIftekhar.springJwt.repository;

import com.helloIftekhar.springJwt.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
        Book findByTitle(String title);
        Book findByISBN(String ISBN);

}
