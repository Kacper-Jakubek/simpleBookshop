package pl.sdacademy.bookstore.service;

/**
 * A class that perform integration test of CategoryService methods
 *
 * <p>An <code>CategoryRepositoryTest</code> check connection with H2 database
 * an tests all method being in CategoryService
 *
 * @author Irek Marszałek
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.model.Category;
import pl.sdacademy.bookstore.repository.CategoryRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryService categoryService;

  /**
   * Clear category table before each test
   */
  @BeforeEach
  void clearBeforeEach(){
    categoryRepository.deleteAll();
  }

  /**
   * Trying to add empty category
   * Should return two error messages
   */
  @Test
  void shouldReturnErrorMsgCategoryCannotBeNull(){
    //given, when, then
    assertThatIllegalArgumentException()
            .isThrownBy(()->categoryService.addCategory(null))
            .withMessage("[Category cannot be null, Category cannot be null]")
            .withNoCause();
  }

  /**
   * Trying to add category with empty name
   * Should return one error messages
   */
  @Test
  void shouldReturnErrorMsgCategoryNameCannotBeNull(){
    //given
    Category category = new Category();
    category.setLeaf(true);
    category.setParentCategory(null);

    //when, then
    assertThatIllegalArgumentException()
            .isThrownBy(()->categoryService.addCategory(category))
            .withMessage("[Category name cannot be null]")
            .withNoCause();
  }

  /**
   * Trying to add category with false leaf
   * Should return one error messages
   */
  @Test
  void shouldReturnErrorMsgCategoryLeafCannotBeFalse(){
    //given
    Category category = new Category();
    category.setName("Root");
    category.setLeaf(false);
    category.setParentCategory(null);

    //when, then
    assertThatIllegalArgumentException()
            .isThrownBy(()->categoryService.addCategory(category))
            .withMessage("[Category leaf cannot be false]")
            .withNoCause();
  }

  /**
   * Creates new category with parent category null
   */
  @Test
  void shouldAddNewCategory() {
    //given
    Category category = new Category();
    category.setName("Root");
    category.setLeaf(true);
    category.setParentCategory(null);

    //when
    Category saved = categoryService.addCategory(category);
    long savedID = saved.getId();
    Category found = categoryService.findById(savedID).orElse(null);

    //then
    assertThat(found).isNotNull();
    assertThat(saved.getId()).isEqualTo(found.getId());
    assertThat(saved.getName()).isEqualTo(found.getName());
    assertThat(saved.isLeaf()).isEqualTo(found.isLeaf());
  }

  /**
   * Creates new category with parent category
   * While adding it should set parent leaf to false
   */
  @Test
  void shouldAddNewCategoryAndSetUpParentCategoryLeafToFalse() {
    //given
    Category parentCategory = new Category();
    parentCategory.setName("Root");
    parentCategory.setLeaf(true);
    parentCategory.setParentCategory(null);
    Category parentCategorySaved = categoryService.addCategory(parentCategory);

    Category category = new Category();
    category.setName("Horror");
    category.setLeaf(true);
    category.setParentCategory(parentCategorySaved);

    //when
    Category saved = categoryService.addCategory(category);
    long savedID = saved.getId();
    Category found = categoryService.findById(savedID).orElse(null);

    long parentCategorySavedId = parentCategorySaved.getId();
    Category foundedParentCategory = categoryService.findById(parentCategorySavedId).orElse(null);

    //then
    assertThat(found).isNotNull();
    assertThat(saved.getId()).isEqualTo(found.getId());
    assertThat(saved.getName()).isEqualTo(found.getName());
    assertThat(saved.isLeaf()).isEqualTo(found.isLeaf());
    assertThat(saved.getParentCategory().getId()).isEqualTo(found.getParentCategory().getId());

    assertThat(foundedParentCategory).isNotNull();
    assertThat(foundedParentCategory.isLeaf()).isFalse();
  }

  @Test
  void shouldChangeExistingCategory() {
    Category category = new Category();
    category.setName("Historyczne");
    category.setLeaf(false);

    Category parentCategory = new Category();
    parentCategory.setName("Książki");
    parentCategory.setLeaf(false);
    Category savedParentCategory = categoryService.addCategory(parentCategory);

    category.setParentCategory(savedParentCategory);

    Category saved = categoryService.addCategory(category);

    saved.setLeaf(true);
    saved.setName("Horrory");
    saved.setParentCategory(null);
    Category updated = categoryService.changeCategory(saved);

    assertThat(updated).isNotNull();
    assertThat(updated.getId()).isEqualTo(saved.getId());
    assertThat(updated.getName()).isEqualTo(saved.getName());
    assertThat(updated.isLeaf()).isEqualTo(saved.isLeaf());
    assertThat(updated.getParentCategory()).isEqualTo(saved.getParentCategory());
  }

  @Test
  void shouldDeleteCategory() {
    Category category = new Category();
    category.setName("Historyczne");
    category.setLeaf(false);

    Category saved = categoryService.addCategory(category);
    long foundId = saved.getId();

    categoryService.deleteCategory(saved);

    Optional<Category> found = categoryService.findById(foundId);

    assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }
}