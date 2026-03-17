package com.example.library.mapper;

import com.example.library.dto.request.AuthorRequestDTO;
import com.example.library.dto.response.AuthorResponseDTO;
import com.example.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponseDTO toResponseDTO(Author author);

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorRequestDTO authorRequestDTO);
}
