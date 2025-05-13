package mate.academy.springboot.web.service;

import mate.academy.springboot.web.dto.BookDto;
import mate.academy.springboot.web.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<BookDto> findAll(int page, int size, String[] sort);

    BookDto findById(Long id);

    BookDto create(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void deleteBook(Long id);
}
