package mate.academy.springboot.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    private String title;
    @NotBlank(message = "Title is mandatory")
    private String author;
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    @NotBlank(message = "Title is mandatory")
    private String isbn;
    private String description;
    private String coverImage;
}
