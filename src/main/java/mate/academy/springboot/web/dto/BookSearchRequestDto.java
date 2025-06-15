package mate.academy.springboot.web.dto;

import lombok.Data;

@Data
public class BookSearchRequestDto {
    private String title;
    private String author;
    private String isbn;
    private String description;
}
