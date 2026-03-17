package com.example.library.service.serviceimpl;

import com.example.library.dto.request.AuthorRequestDTO;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.AuthorResponseDTO;
import com.example.library.mapper.AuthorMapper;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public ApiResponse<List<AuthorResponseDTO>> getAuthors() {
        List<AuthorResponseDTO> authorResponseDTO = authorRepository.findAll().stream().map(authorMapper::toResponseDTO).toList();
        return ApiResponse.<List<AuthorResponseDTO>>builder()
                .success(true)
                .message("Successfully Retrieve Authors")
                .data(authorResponseDTO)
                .build();
    }

    @Override
    public ApiResponse<AuthorResponseDTO> createAuthors(AuthorRequestDTO authorRequestDTO) {

        Author authorEntity = authorMapper.toEntity(authorRequestDTO);
        log.info("here is the result ={}",authorEntity.getName());
        Author savedAuthor = authorRepository.save(authorEntity);
        AuthorResponseDTO authorResponseDTO = authorMapper.toResponseDTO(savedAuthor);
        return ApiResponse.<AuthorResponseDTO>builder()
                .success(true)
                .message("Successfully Create Authors")
                .data(authorResponseDTO)
                .build();
    }
}
