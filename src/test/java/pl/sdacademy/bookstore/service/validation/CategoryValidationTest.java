package pl.sdacademy.bookstore.service.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.model.dto.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryValidationTest {

  Category category = new Category();

  @Autowired
  CategoryValidation categoryValidation;

  @Test
  void shouldReturnOneMessageCategoryCannotBeNull() {
    List<String> errorMessages = categoryValidation.checkIfNewNameIsCorrect(null);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category cannot be null");
  }

  @Test
  void shouldReturnOneMessageCategoryNameCannotBeNull() {
    List<String> errorMessages = categoryValidation.checkIfNewNameIsCorrect(category);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category name cannot be null");
  }

  @Test
  void shouldReturnOneMessageCategoryNameCannotBeShorterThan3Chars() {
    category.setName("12");
    List<String> errorMessages = categoryValidation.checkIfNewNameIsCorrect(category);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category name cannot be shorter than 3 chars");
  }

  @Test
  void shouldReturnOneMessageParentCategoryCannotBeNull() {
    List<String> errorMessages = categoryValidation.checkIfParentCategoryIsCorrect(category);
    assertThat(errorMessages).isNotEmpty();
    assertThat(errorMessages.size()).isEqualTo(1);
    assertThat(errorMessages.get(0)).isEqualTo("Category parent cannot be null");
  }

  @Test
  void shouldReturnEmptyErrorMessages() {
    category.setId(1);
    category.setName("Name");
    category.setParentCategory(category);
    List<String> errorMessages = categoryValidation.checkIfCanBeCreated(category);

    assertThat(errorMessages).isEmpty();
  }
}