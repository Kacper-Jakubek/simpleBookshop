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

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
  CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
  CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * Add a new category.
   * @param category DTO instance
   * @return newly added DTO category
   *
   */
  public Category addCategory(Category category){
    //TODO: dorób validację dodawania kategorii
    CategoryEntity categoryEntity = categoryMapper.map(category);
    CategoryEntity saveCategory = categoryRepository.save(categoryEntity);
    return categoryMapper.map(saveCategory);
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
   * @return List of children categories
   *
   */
  public List<Category> findChildren(long id){
    return categoryMapper.map(categoryRepository.findAllChildCategories(id));
  }


}
