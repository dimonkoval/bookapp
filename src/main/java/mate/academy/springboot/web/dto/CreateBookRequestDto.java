package mate.academy.springboot.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Author is mandatory")
    private String author;
    @Positive(message = "Price must be positive")
    @NotNull(message = "Price is mandatory")
    private BigDecimal price;
    @NotBlank(message = "ISBN is mandatory")
    @Pattern(
            regexp = "\\d{13}",
            message = "ISBN must be exactly 13 digits"
    )
    private String isbn;
    private String description;
    private String coverImage;
}
