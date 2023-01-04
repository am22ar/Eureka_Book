package com.books.book_bookstore.repository;

import com.books.book_bookstore.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel,Long> {
    @Query(value = "select * from book where book_name=:bookname",nativeQuery = true)
    List<BookModel> findByBookName(String bookname);
    @Query(value = " select * from book order by price asc;",nativeQuery = true)
    List<BookModel> sortAscending();
    @Query(value = " select * from book order by price desc;",nativeQuery = true)
    List<BookModel> sortDescending();
}
