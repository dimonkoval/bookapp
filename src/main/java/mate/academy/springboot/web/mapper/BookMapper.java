package mate.academy.springboot.web.mapper;

import mate.academy.springboot.web.config.MapperConfig;
import mate.academy.springboot.web.dto.BookDto;
import mate.academy.springboot.web.dto.CreateBookRequestDto;
import mate.academy.springboot.web.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(target = "author", source = "requestDto.author")
    @Mapping(target = "price", source = "requestDto.price")
    @Mapping(target = "isbn", source = "requestDto.isbn")
    @Mapping(target = "description", source = "requestDto.description")
    @Mapping(target = "coverImage", source = "requestDto.coverImage")
    Book updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);
}
