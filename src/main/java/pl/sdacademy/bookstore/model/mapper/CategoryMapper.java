package pl.sdacademy.bookstore.model.mapper;

/* By IM */
/*It maps entity to dto*/

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.dto.Category;

@Mapper
@Component
public interface CategoryMapper {
  CategoryEntity map(Category category);
  Category map(CategoryEntity categoryEntity);
}
