package mate.academy.springboot.web.service;

import mate.academy.springboot.web.dto.BookDto;
import mate.academy.springboot.web.dto.BookSearchRequestDto;
import mate.academy.springboot.web.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto create(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void deleteBook(Long id);

    Page<BookDto> searchBooks(BookSearchRequestDto request, Pageable pageable);
}
