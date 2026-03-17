package com.example.library.controller;

import com.example.library.dto.request.AuthorRequestDTO;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.AuthorResponseDTO;
import com.example.library.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author/api")
@Tag(name = "Author API", description = "Api for author")
@Slf4j
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<AuthorResponseDTO>>> getAuthors() {
        return ResponseEntity.ok().body(authorService.getAuthors());
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<AuthorResponseDTO>> createAuthors(@Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        log.info("Create Author Request: " + authorRequestDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthors(authorRequestDTO));
    }
}
