package pl.sdacademy.bookstore.service.validation;

/**
 * A class that perform unit test of CategoryValidationAddNew methods
 *
 * <p>An <code>CategoryValidationAddNewTest</code> tests all validations methods
 * used for adding new category
 *
 * @author Irek Marszałek
 */

import org.junit.jupiter.api.Test;
import pl.sdacademy.bookstore.model.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryValidationAddNewTest {

  CategoryValidationAddNew categoryValidationAddNew = new CategoryValidationAddNew();

  /**
   * Trying to add empty category
   */
  @Test
  void shouldReturnOneMessageCategoryCannotBeNull() {
    //given, when
    List<String> errorMessages = categoryValidationAddNew.checkName(null);

    //then
    assertThat(errorMessages)
            .isNotEmpty();
    assertThat(errorMessages.size())
            .isEqualTo(1);
    assertThat(errorMessages.get(0))
            .isEqualTo("Category cannot be null");
  }

  /**
   * Trying to add category with empty name
   */
  @Test
  void shouldReturnOneMessageCategoryNameCannotBeNull() {
    //given
    Category category = new Category();

    //when
    List<String> errorMessages = categoryValidationAddNew.checkName(category);

    //then
    assertThat(errorMessages)
            .isNotEmpty();
    assertThat(errorMessages.size())
            .isEqualTo(1);
    assertThat(errorMessages.get(0))
            .isEqualTo("Category name cannot be null");
  }

  /**
   * Trying to add category with empty name
   */
  @Test
  void shouldReturnOneMessageCategoryNameCannotBeShorterThan3Chars() {
    //given
    Category category = new Category();
    category.setName("12");

    //when
    List<String> errorMessages = categoryValidationAddNew.checkName(category);

    //then
    assertThat(errorMessages)
            .isNotEmpty();
    assertThat(errorMessages.size())
            .isEqualTo(1);
    assertThat(errorMessages.get(0))
            .isEqualTo("Category name cannot be shorter than 3 chars");
  }

  /**
   * Trying to add category with false leaf
   */
  @Test
  void shouldReturnMessageLeafCannotBeFalse() {
    //given
    Category category = new Category();
    category.setName("123");

    //when
    List<String> errorMessages = categoryValidationAddNew.checkLeaf(category);

    //then
    assertThat(errorMessages)
            .isNotEmpty();
    assertThat(errorMessages.size())
            .isEqualTo(1);
    assertThat(errorMessages.get(0))
            .isEqualTo("Category leaf cannot be false");
  }

  /**
   * Trying to add complete category
   */
  @Test
  void shouldReturnEmptyErrorMessages() {
    //given
    Category category = new Category();
    category.setId(1);
    category.setName("Name");
    category.setParentCategory(null);
    category.setLeaf(true);

    //when
    List<String> errorMessages = categoryValidationAddNew.checkCategory(category);

    //then
    assertThat(errorMessages).isEmpty();
  }
}