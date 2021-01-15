package pl.sdacademy.bookstore.mapper;

import org.mapstruct.Mapper;
import pl.sdacademy.bookstore.db.AuthorEntity;
import pl.sdacademy.bookstore.model.Author;

@Mapper
public interface AuthorMapper {
    AuthorEntity map(Author author);
    Author map(AuthorEntity authorEntity);

}
