package pl.sdacademy.bookstore.service.validation;

import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.model.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryValidationAddNewTest {

  Category category = new Category();
  CategoryValidationAddNew categoryValidationAddNew = new CategoryValidationAddNew();

  @Test
  void shouldReturnOneMessageCategoryCannotBeNull() {
    List<String> errorMessages = categoryValidationAddNew.checkName(null);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category cannot be null");
  }

  @Test
  void shouldReturnOneMessageCategoryNameCannotBeNull() {
    List<String> errorMessages = categoryValidationAddNew.checkName(category);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category name cannot be null");
  }

  @Test
  void shouldReturnOneMessageCategoryNameCannotBeShorterThan3Chars() {
    category.setName("12");
    List<String> errorMessages = categoryValidationAddNew.checkName(category);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category name cannot be shorter than 3 chars");
  }

  @Test
  void shouldReturnMessageLeafCannotBeFalse() {
    category.setName("12");
    List<String> errorMessages = categoryValidationAddNew.checkLeaf(category);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category leaf cannot be false");
  }

  @Test
  void shouldReturnEmptyErrorMessages() {
    category.setId(1);
    category.setName("Name");
    category.setParentCategory(category);
    category.setLeaf(true);
    List<String> errorMessages = categoryValidationAddNew.checkCategory(category);

    assertThat(errorMessages).isEmpty();
  }
}