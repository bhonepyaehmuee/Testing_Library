package com.example.library.service;


import com.example.library.dto.request.BookRequestDTO;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.BookResponseDTO;

import java.util.List;

public interface BookService {
    ApiResponse<List<BookResponseDTO>> getAllBooks();

    ApiResponse<BookResponseDTO> createBook(BookRequestDTO bookRequestDTO);

    ApiResponse<BookResponseDTO> getBookByIsbn(String isbn);
}
