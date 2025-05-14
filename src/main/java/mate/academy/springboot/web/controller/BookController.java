package mate.academy.springboot.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.web.dto.BookDto;
import mate.academy.springboot.web.dto.CreateBookRequestDto;
import mate.academy.springboot.web.service.BookService;
import mate.academy.springboot.web.swagger.annotations.BadRequestApiResponse;
import mate.academy.springboot.web.swagger.annotations.CreatedApiResponse;
import mate.academy.springboot.web.swagger.annotations.NoContentApiResponse;
import mate.academy.springboot.web.swagger.annotations.NotFoundApiResponse;
import mate.academy.springboot.web.swagger.annotations.OkApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books with pagination and sorting")
    @OkApiResponse
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by its ID",
            description = "Retrieve a single book by its unique ID")
    @CreatedApiResponse
    @BadRequestApiResponse
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new book",
            description = "Add a new book to the collection")
    @CreatedApiResponse
    @BadRequestApiResponse
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.create(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book",
            description = "Update the details of an existing book")
    @OkApiResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateBook(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book",
            description = "Delete a book by its ID from the collection")
    @NoContentApiResponse
    @NotFoundApiResponse
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
