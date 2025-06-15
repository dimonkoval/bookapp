package mate.academy.springboot.web.repository;

import mate.academy.springboot.web.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
