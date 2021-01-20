package pl.sdacademy.bookstore.service;
/**
 * A class that that collects function to operate on a Category.
 *
 * <p>An <code>CategoryService</code> instance is supposed to be a set of
 * methods that allows to work with category
 *
 * @author Irek Marszałek
 */

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.Category;
import pl.sdacademy.bookstore.mapper.CategoryMapper;
import pl.sdacademy.bookstore.repository.CategoryRepository;
import pl.sdacademy.bookstore.service.validation.CategoryValidationAddNew;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
  CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
  CategoryRepository categoryRepository;
  CategoryValidationAddNew categoryValidationAddNew;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository, CategoryValidationAddNew categoryValidationAddNew) {
    this.categoryRepository = categoryRepository;
    this.categoryValidationAddNew = categoryValidationAddNew;
  }

  /**
   * Add a new category.
   * Validate if category can be added
   * In case parent is added it modifies also leaf in parent category. It set leaf to false.
   * @param category DTO instance
   * @return newly added DTO category
   *
   */
  public Category addCategory(Category category){
    List<String> validationErrorMessages = categoryValidationAddNew.checkCategory(category);

    if (validationErrorMessages.isEmpty()){
      CategoryEntity categoryEntity = categoryMapper.map(category);
      CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

      //if parent is assigned then update parent category leaf to false
      if(category.getParentCategory() != null){
          //TODO: add changing category validation
          Category parentCategory = category.getParentCategory();
          parentCategory.setLeaf(false);
          changeCategory(parentCategory);
      }
      return categoryMapper.map(savedCategory);
    }else {
      throw new IllegalArgumentException(validationErrorMessages.toString());
    }
  }

  /**
   * Change existing category.
   * @param category DTO instance
   * @return changed DTO category
   *
   */
  public Category changeCategory(Category category){
    //TODO: dorób validację zmiany kategorii
    CategoryEntity categoryEntity = categoryMapper.map(category);
    CategoryEntity savedCategory = categoryRepository.update(categoryEntity);
    return categoryMapper.map(savedCategory);
  }

  /**
   * Delete existing category.
   *
   * @param category DTO instance
   * @return void
   *
   */
  public void deleteCategory(Category category){
    //TODO: dorób validację usuwania kategorii
    CategoryEntity categoryEntity = categoryMapper.map(category);
    categoryRepository.delete(categoryEntity);
  }

  /**
   * Looking for Category having provided id.
   * @param id category id
   * @return empty optional if could not find, optional with category if managed to find
   *
   */
  public Optional<Category> findById(long id){
    Optional<CategoryEntity> found = categoryRepository.getById(id);
    return found.map(categoryEntity -> categoryMapper.map(categoryEntity));
  }

  /**
   * Looking for children categories.
   * @param id category id
   * @return List of children categories (without grandchildren etc, only children)
   *
   */
  public List<Category> findChildren(long id){
    return categoryMapper.map(categoryRepository.findAllChildren(id));
  }

  /**
   * Counting children of provided category.
   * @param id category id
   * @return Number (int) of founded children (but without grandchildren, don't forget it ;) )
   *
   */
  public int countChildren(long id){
    return categoryRepository.countChildren(id);
  }

}
