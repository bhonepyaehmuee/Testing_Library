package com.example.library.mapper;

import com.example.library.dto.request.BookRequestDTO;
import com.example.library.dto.response.BookResponseDTO;
import com.example.library.model.Book;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-17T14:27:55+0630",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toEntity(BookRequestDTO bookRequestDTO) {
        if ( bookRequestDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthors( mapStringToAuthors( bookRequestDTO.getAuthors() ) );
        book.setName( bookRequestDTO.getName() );
        book.setIsbn( bookRequestDTO.getIsbn() );
        book.setPrice( bookRequestDTO.getPrice() );

        return book;
    }

    @Override
    public BookResponseDTO toResponseDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        bookResponseDTO.setAuthor( mapAuthorsToString( book.getAuthors() ) );
        bookResponseDTO.setId( book.getId() );
        bookResponseDTO.setName( book.getName() );
        bookResponseDTO.setIsbn( book.getIsbn() );
        bookResponseDTO.setPrice( book.getPrice() );

        return bookResponseDTO;
    }
}
