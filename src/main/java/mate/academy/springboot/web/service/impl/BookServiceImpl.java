package mate.academy.springboot.web.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final String BOOK_NOT_FOUND = "Book with id %d not found";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_AUTHOR = "author";
    private static final String FIELD_ISBN = "isbn";
    private static final String FIELD_DESCRIPTION = "description";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @PersistenceContext
    private EntityManager entityManager;

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

    public Page<BookDto> searchBooks(BookSearchRequestDto request, Pageable pageable) {
        List<Book> books = getBooks(request, pageable);
        long total = countBooks(request);

        return new PageImpl<>(
                books.stream().map(bookMapper::toDto).toList(),
                pageable,
                total
        );
    }

    private List<Book> getBooks(BookSearchRequestDto request, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        Predicate[] predicates = buildPredicates(cb, root, request);
        cq.where(predicates);

        TypedQuery<Book> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    private long countBooks(BookSearchRequestDto request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> root = cq.from(Book.class);

        Predicate[] predicates = buildPredicates(cb, root, request);
        cq.select(cb.count(root)).where(predicates);

        return entityManager.createQuery(cq).getSingleResult();
    }

    private Predicate[] buildPredicates(CriteriaBuilder cb,
                                        Root<Book> root,
                                        BookSearchRequestDto request) {
        List<Predicate> predicates = new ArrayList<>();

        if (request.getTitle() != null) {
            predicates.add(cb.like(cb.lower(root.get(FIELD_TITLE)),
                    "%" + request.getTitle().toLowerCase() + "%"));
        }
        if (request.getAuthor() != null) {
            predicates.add(cb.like(cb.lower(root.get(FIELD_AUTHOR)),
                    "%" + request.getAuthor().toLowerCase() + "%"));
        }
        if (request.getIsbn() != null) {
            predicates.add(cb.like(cb.lower(root.get(FIELD_ISBN)),
                    "%" + request.getIsbn().toLowerCase() + "%"));
        }
        if (request.getDescription() != null) {
            predicates.add(cb.like(cb.lower(root.get(FIELD_DESCRIPTION)),
                    "%" + request.getDescription().toLowerCase() + "%"));
        }

        return predicates.toArray(Predicate[]::new);
    }
}
