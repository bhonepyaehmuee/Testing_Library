package com.example.library.service.serviceimpl;

import com.example.library.dto.request.BookRequestDTO;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.BookResponseDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public ApiResponse<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> bookResponseDTOS = bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponseDTO).toList();
        return ApiResponse.<List<BookResponseDTO>>builder()
                .success(true)
                .message("retrieve all books")
                .data(bookResponseDTOS)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<BookResponseDTO> createBook(BookRequestDTO bookRequestDTO)
    {

        Book book = bookMapper.toEntity(bookRequestDTO);
        book = bookRepository.save(book);

        // Change this line to avoid calling .size() on a potential null

        BookResponseDTO bookResponseDTO = bookMapper.toResponseDTO(book);
        return ApiResponse.<BookResponseDTO>builder()
                .success(true)
                .message("Successfully Created Books")
                .data(bookResponseDTO)
                .build();
    }

    @Override
    public ApiResponse<BookResponseDTO> getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book with ISBN " + isbn + " not found"));

        BookResponseDTO bookResponseDTO = bookMapper.toResponseDTO(book);

        return ApiResponse.<BookResponseDTO>builder()
                .success(true)
                .message("Book retrieved successfully")
                .data(bookResponseDTO)
                .build();
    }

}
