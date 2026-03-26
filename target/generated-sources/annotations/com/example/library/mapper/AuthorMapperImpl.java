package com.example.library.mapper;

import com.example.library.dto.request.AuthorRequestDTO;
import com.example.library.dto.response.AuthorResponseDTO;
import com.example.library.model.Author;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-27T01:06:16+0630",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorResponseDTO toResponseDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();

        authorResponseDTO.setId( author.getId() );
        authorResponseDTO.setName( author.getName() );

        return authorResponseDTO;
    }

    @Override
    public Author toEntity(AuthorRequestDTO authorRequestDTO) {
        if ( authorRequestDTO == null ) {
            return null;
        }

        Author author = new Author();

        author.setName( authorRequestDTO.getName() );

        return author;
    }
}
