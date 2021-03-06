package pl.sdacademy.bookstore.mapper;

/**
 * A class that will map between Category and CategoryEntity
 *
 * <p>An <code>CategoryMapper</code> instance is supposed to be a tool
 * that will transform Category to CategoryEntity and the other way as well
 *
 * @author Irek Marszałek
 */

import org.mapstruct.Mapper;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
  CategoryEntity map(Category category);
  Category map(CategoryEntity categoryEntity);
  List<Category> map(List<CategoryEntity> categoryEntities);
}
