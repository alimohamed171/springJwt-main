package com.helloIftekhar.springJwt.repository;

import com.helloIftekhar.springJwt.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowBookRepo extends JpaRepository<BorrowedBook,Integer> {


//    List<BorrowedBook> findByUserId(int userId);
//Optional<BorrowedBook> findByUserIdAndBookId(int userId, int bookId);

}
