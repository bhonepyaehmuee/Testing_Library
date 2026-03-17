package com.example.library.service;

import com.example.library.dto.request.AuthorRequestDTO;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.AuthorResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface AuthorService {
    ApiResponse<List<AuthorResponseDTO>> getAuthors();

    ApiResponse<AuthorResponseDTO> createAuthors(@Valid AuthorRequestDTO authorRequestDTO);
}
