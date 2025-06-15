package mate.academy.springboot.web.service.impl;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.web.dto.BookDto;
import mate.academy.springboot.web.dto.BookSearchRequestDto;
import mate.academy.springboot.web.dto.CreateBookRequestDto;
import mate.academy.springboot.web.exception.EntityNotFoundException;
import mate.academy.springboot.web.mapper.BookMapper;
import mate.academy.springboot.web.model.Book;
import mate.academy.springboot.web.repository.BookRepository;
import mate.academy.springboot.web.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final String BOOK_NOT_FOUND = "Book with id %d not found";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(BOOK_NOT_FOUND, id)));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto create(CreateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public BookDto updateBook(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(BOOK_NOT_FOUND, id)));
        bookMapper.updateBookFromDto(requestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format(BOOK_NOT_FOUND, id));
        }
    }

    @Override
    public Page<BookDto> searchBooks(BookSearchRequestDto request, Pageable pageable) {
        Map<String, Function<String, Specification<Book>>> fieldSpecMap = Map.of(
                "title", value -> (root, q, cb) -> cb.like(cb.lower(root.get("title")),
                        "%" + value.toLowerCase() + "%"),
                "author", value -> (root, q, cb) -> cb.like(cb.lower(root.get("author")),
                        "%" + value.toLowerCase() + "%"),
                "isbn", value -> (root, q, cb) -> cb.like(cb.lower(root.get("isbn")),
                        "%" + value.toLowerCase() + "%"),
                "description", value -> (root, q, cb) -> cb.like(cb.lower(root.get("description")),
                        "%" + value.toLowerCase() + "%")
        );

        Specification<Book> spec = Specification.where(null);

        for (Field field : BookSearchRequestDto.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(request);
                if (value != null && fieldSpecMap.containsKey(field.getName())) {
                    spec = spec.and(fieldSpecMap.get(field.getName()).apply(value.toString()));
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to read search field: " + field.getName(), e);
            }
        }
        return bookRepository.findAll(spec, pageable).map(bookMapper::toDto);
    }
}
