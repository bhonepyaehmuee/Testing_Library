    package com.example.library.controller;

    import com.example.library.dto.request.BookRequestDTO;
    import com.example.library.dto.response.ApiResponse;
    import com.example.library.dto.response.BookResponseDTO;
    import com.example.library.model.Book;
    import com.example.library.service.BookService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/books")
    @Tag(name = "Book Api", description = "Book Management!")
    public class BookController {
        private final BookService bookService;

        public BookController(BookService bookService) {
            this.bookService = bookService;
        }

        @GetMapping
        @Operation(summary = "Get all Books")
        public ResponseEntity<ApiResponse<List<BookResponseDTO>>> getAllBooks() {
    //            bookService.getAllBooks();
            return ResponseEntity.ok().body(bookService.getAllBooks());
        }

        @PostMapping
        @Operation(summary = "Create the Book")
        public ResponseEntity<ApiResponse<BookResponseDTO>> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequestDTO));
        }
        @GetMapping("/{isbn}")
        public ResponseEntity<ApiResponse<BookResponseDTO>> getBook(@PathVariable String isbn) {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookByIsbn(isbn));
        }
    }
