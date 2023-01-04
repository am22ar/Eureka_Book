package com.books.book_bookstore.service;

import com.books.book_bookstore.dto.BookDto;
import com.books.book_bookstore.exception.BookStoreException;
import com.books.book_bookstore.model.BookModel;
import com.books.book_bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{
    List<BookModel> bookModelList = new ArrayList<>();
    @Autowired
    BookRepository bookRepository;

    @Override
    public BookModel insertBook(BookDto bookDto) {
        BookModel bookModel = new BookModel(bookDto);
        return bookRepository.save(bookModel);
    }
    @Override
    public List<BookModel> getAllBooks() {
        bookModelList = bookRepository.findAll();
        return bookModelList;
    }

    @Override
    public BookModel getBookById(Long bookId) {
        Optional<BookModel> getBookId = bookRepository.findById(bookId);
        if(getBookId == null){
            throw new BookStoreException("Book with id: '"+getBookId+"' not found");
        }
        else {
            return getBookId.get();
        }
    }
    @Override
    public List<BookModel> getBookByName(String bookName) {
        List<BookModel> bookModel = bookRepository.findByBookName(bookName);
        if(bookModel != null){
            return bookModel;
        }else {
            throw new BookStoreException("Book with this name: '"+bookName+"' not found");
        }
    }
    @Override
    public BookModel updateById(Long bookId, BookDto bookDto){
        Optional<BookModel> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new BookStoreException("Book Record doesn't exists for this Id: '"+bookId+"'");
        } else {
            BookModel newBook = new BookModel(bookId,bookDto);
            return bookRepository.save(newBook);
        }
    }
    @Override
    public List<BookModel> sortAscending() {
        bookModelList = bookRepository.sortAscending();
        return bookModelList;
    }
    @Override
    public List<BookModel> sortDescending() {
        bookModelList = bookRepository.sortDescending();
        return bookModelList;
    }
    @Override
    public Optional<BookModel> deleteById(Long bookId) {
        Optional<BookModel> bookModel = bookRepository.findById(bookId);
        if(bookModel.isPresent()){
            bookRepository.deleteById(bookId);
            return bookModel;
        }else {
            throw new BookStoreException("Book with Id: '"+bookId+"' doesn't exists");
        }
    }
    @Override
    public BookModel updateQuantity(Long bookId, BookDto bookDto) {
        BookModel bookModel = bookRepository.findById(bookId).get();
        if (bookModel != null){
            bookModel.setQuantity(bookDto.getQuantity());
            return bookRepository.save(bookModel);
        }else {
            throw new BookStoreException("Quantity not updated for this Id: '"+bookId+"' ");
        }
    }
}
