package pl.sdacademy.bookstore.service;
/* By IM */
/*Functionality necessary to serve Categories*/

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
