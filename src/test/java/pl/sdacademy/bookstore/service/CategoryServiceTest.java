package pl.sdacademy.bookstore.service;
/* By IM */
/*Category service tests*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.repository.CategoryRepository;

import static org.assertj.core.api.Assertions.assertThat;

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
    Category category = new Category();
    category.setName("Historyczne");
    category.setLeaf(false);

    Category parentCategory = new Category();
    parentCategory.setName("Książki");
    parentCategory.setLeaf(false);
    Category savedParentCategory = categoryService.addCategory(parentCategory);

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
}