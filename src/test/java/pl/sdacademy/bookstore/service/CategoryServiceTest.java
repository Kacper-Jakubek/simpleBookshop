package pl.sdacademy.bookstore.service;

/**
 * A class that perform integration test of CategoryService
 *
 * <p>An <code>CategoryRepositoryTest</code> check connection witt H2 database
 * an tests all method being in CategoryService
 *
 * @author Irek Marszałek
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.repository.CategoryRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class CategoryServiceTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryService categoryService;

  @BeforeEach
  void clearBeforeEach(){
    categoryRepository.deleteAll();
  }

  @Test
  void shouldAddNewCategory() {
    Category parentCategory = new Category();
    parentCategory.setName("Książki");
    parentCategory.setLeaf(false);
    Category savedParentCategory = categoryService.addCategory(parentCategory);

    Category category = new Category();
    category.setName("Historyczne");
    category.setLeaf(false);
    category.setParentCategory(savedParentCategory);
    Category saved = categoryService.addCategory(category);

    long savedID = saved.getId();

    CategoryEntity found = categoryRepository.getById(savedID).orElse(new CategoryEntity());

    assertThat(saved.getId()).isEqualTo(found.getId());
    assertThat(saved.getName()).isEqualTo(found.getName());
    assertThat(saved.isLeaf()).isEqualTo(found.isLeaf());

    assertThat(found.getParentCategory().getId()).isEqualTo(savedParentCategory.getId());
    assertThat(found.getParentCategory().getName()).isEqualTo(savedParentCategory.getName());
    assertThat(found.getParentCategory().isLeaf()).isEqualTo(savedParentCategory.isLeaf());
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