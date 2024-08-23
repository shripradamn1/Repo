package com.example.spring_demo.Service;


import com.example.spring_demo.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Integer> {
}