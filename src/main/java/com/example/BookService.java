package com.example;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final Map<String, Book> catalog = new ConcurrentHashMap<>();
 
    public void add(Book book) {
        catalog.put(book.getIsbn(), book);
    }

    public Optional<Book> find(String isbn) {
        return Optional.ofNullable(catalog.get(isbn));
    }

    public void update(String isbn, Book book) {
        catalog.put(isbn, book);
    }
}
