package com.example.library.mapper;

import com.example.library.dto.request.BookRequestDTO;
import com.example.library.dto.response.BookResponseDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "authors", target = "authors")
    Book toEntity(BookRequestDTO bookRequestDTO);

    @Mapping(source = "authors", target = "author")
    BookResponseDTO toResponseDTO(Book book);

    default List<Author> mapStringToAuthors(String authorsString) {
        if (authorsString == null || authorsString.isBlank()) {
            return new java.util.ArrayList<>();
        }

        return java.util.Arrays.stream(authorsString.split(","))
                .map(name -> {
                    Author author = new Author();
                    author.setName(name.trim());
                    return author;
                }).toList();
    }

    default String mapAuthorsToString(List<Author> authors) {
        if (authors == null || authors.isEmpty()) return "";

        return authors.stream()
                .map(Author::getName)
                .collect(java.util.stream.Collectors.joining(", "));
    }
}
