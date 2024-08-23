package com.example.spring_demo.Controller;

import com.example.spring_demo.Common.BookNotFoundException;
import com.example.spring_demo.Models.Book;
import com.example.spring_demo.Service.BookRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class BookController {
  @Autowired
  private BookRepository bookRepository;

  @PostConstruct
  public void init() {
    bookRepository.save(new Book(1, "Book One", 100.00));
    bookRepository.save(new Book(2, "Book Two", 200.00));
    bookRepository.save(new Book(3, "Book Three", 300.00));
  }

  @GetMapping("/books")
  public ResponseEntity<?> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    if (books.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      return ResponseEntity.ok(books);
    }
  }

  @GetMapping("/bookid/{id}")
  public Book getBookById(@PathVariable int id) {
    return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
  }

  @PostMapping("/addBook")
  public String addBook() {
    Book book = new Book();
    book.setTitle("Hardcoded Title");
    book.setPrice(999.99);
    bookRepository.save(book);
    return "The book is added!";
  }

  @PutMapping("/updateBook/{id}")
  public Book updateBook(@PathVariable int id) {
    return bookRepository.findById(id)
            .map(book -> {
              book.setTitle("Updated Hardcoded Title");
              book.setPrice(1234.56);
              return bookRepository.save(book);
            })
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
  }
  @DeleteMapping("/bookid/{id}")
  public String deleteBook(@PathVariable int id) {
    if (!bookRepository.existsById(id)) {
      throw new BookNotFoundException("Book with ID " + id + " not found");
    }
    bookRepository.deleteById(id);
    return "The book is deleted!";
  }
}
