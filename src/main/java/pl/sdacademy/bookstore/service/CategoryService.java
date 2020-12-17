package pl.sdacademy.bookstore.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.model.mapper.CategoryMapper;
import pl.sdacademy.bookstore.repository.CategoryRepository;

@AllArgsConstructor
@Service
public class CategoryService {
  CategoryMapper categoryMapper;
  CategoryRepository categoryRepository;

  public Category addCategory(Category category){
    CategoryEntity categoryEntity = categoryMapper.map(category);
    categoryRepository.save(categoryEntity);
    return category;
  }
}
