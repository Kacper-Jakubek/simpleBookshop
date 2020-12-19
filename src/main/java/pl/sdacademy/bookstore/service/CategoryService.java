package pl.sdacademy.bookstore.service;

/**
 * A class that that collects function to operate on a Category
 *
 * <p>An <code>CategoryService</code> instance is supposed to be a set
 * methods that allows to work with category
 *
 * @author Irek Marszałek
 */

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.model.mapper.CategoryMapper;
import pl.sdacademy.bookstore.repository.CategoryRepository;

@Service
public class CategoryService {
  CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
  CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category addCategory(Category category){
    //TODO dorób validację
    CategoryEntity categoryEntity = categoryMapper.map(category);
    CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
    return categoryMapper.map(savedCategory);
  }
}
