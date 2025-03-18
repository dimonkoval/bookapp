package mate.academy.springboot.web.service;

import java.util.List;
import mate.academy.springboot.web.dto.BookDto;
import mate.academy.springboot.web.dto.CreateBookRequestDto;

public interface BookService {
    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto create(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void deleteBook(Long id);
}
