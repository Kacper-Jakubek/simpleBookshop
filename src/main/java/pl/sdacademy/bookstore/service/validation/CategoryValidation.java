package pl.sdacademy.bookstore.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.service.CategoryService;

@Component
public class CategoryValidation {
  private final CategoryService categoryService;

  @Autowired
  public CategoryValidation(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  void checkIfNewNameIsCorrect(Category category){
    if (category == null){
      throw new IllegalArgumentException("Category cannot be null");
    }else {
      if(category.getName() == null){
        throw new IllegalArgumentException("Category name cannot be null");
      }else {
        if (category.getName().length() < 3) {
          throw new IllegalArgumentException("Category name cannot be shorter than 3 chars");
        }
      }
    }
  }

  void checkIfParentCategoryIsCorect(Category category){
    if (category == null){
      throw new IllegalArgumentException("Category cannot be null");
    }else {
      if(category.getParentCategory() == null){
        throw new IllegalArgumentException("Category parent cannot be null");
      }else {
        long id = category.getId();
        long parentId = category.getParentCategory().getId();
        if(id!=parentId){
          if(category.isLeaf()){

          }
        }
      }
    }
  }
}
