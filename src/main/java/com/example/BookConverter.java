package com.example;

import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;

import org.springframework.stereotype.Component;

@Component
public class BookConverter {
    public Book fromJson(JsonObject json) {
        final Book book = new Book();
        book.setTitle(json.getString("title"));
        book.setIsbn(json.getString("isbn"));
        book.setAuthors(
            json
                .getJsonArray("authors")
                .stream()
                .map(value -> (JsonString)value)
                .map(JsonString::getString)
                .collect(Collectors.toList()));
        return book;
    }

    public JsonObject toJson(Book book) {
        return Json
            .createObjectBuilder()
            .add("title", book.getTitle())
            .add("isbn", book.getIsbn())
            .add("authors", Json.createArrayBuilder(book.getAuthors()))
            .build();
    }
}
