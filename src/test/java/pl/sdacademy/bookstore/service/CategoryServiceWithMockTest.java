package pl.sdacademy.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.Category;
import pl.sdacademy.bookstore.mapper.CategoryMapper;
import pl.sdacademy.bookstore.repository.CategoryRepository;
import pl.sdacademy.bookstore.service.validation.CategoryValidationAddNew;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceWithMockTest {
  //given
  private static final long PARENT_CATEGORY_ID = 1;
  private static final String PARENT_CATEGORY_NAME = "Root";
  private static final CategoryEntity PARENT_CATEGORY_PARENT = null;
  private static final CategoryEntity PARENT_CATEGORY = new CategoryEntity(
          PARENT_CATEGORY_ID
          , PARENT_CATEGORY_NAME
          , PARENT_CATEGORY_PARENT
          , true);

  private static final long CATEGORY_ID = 2;
  private static final String CATEGORY_NAME = "Horror";
  private static final CategoryEntity CATEGORY_PARENT = PARENT_CATEGORY;
  private static final CategoryEntity CATEGORY_ENTITY = new CategoryEntity(CATEGORY_ID
          , CATEGORY_NAME
          , CATEGORY_PARENT
          , true);

  private static final long CATEGORY_WITH_EMPTY_NAME_ID = 3;
  private static final String CATEGORY_WITH_EMPTY_NAME_NAME = null;
  private static final CategoryEntity CATEGORY_WITH_EMPTY_NAME_PARENT_CATEGORY = null;
  private static final CategoryEntity CATEGORY_WITH_EMPTY_NAME = new CategoryEntity(CATEGORY_WITH_EMPTY_NAME_ID
          , CATEGORY_WITH_EMPTY_NAME_NAME
          , CATEGORY_WITH_EMPTY_NAME_PARENT_CATEGORY
          ,true);


  CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

  @Mock
  CategoryRepository categoryRepository;

  @Mock
  CategoryValidationAddNew categoryValidationAddNew;

  @InjectMocks
  CategoryService categoryService;

  /**
   * Trying to add empty category
   * Should return two error messages
   */
  @Test
  void shouldReturnErrorMsgCategoryCannotBeNull(){
    //given
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add("Category cannot be null");
    errorMessages.add("Category cannot be null");

    //when
    when(categoryValidationAddNew.checkCategory(null)).thenReturn(errorMessages);

    //then
    assertThatIllegalArgumentException()
            .isThrownBy(()->categoryService.addCategory(null))
            .withMessage("[Category cannot be null, Category cannot be null]")
            .withNoCause();

    verify(categoryValidationAddNew).checkCategory(null);
    verifyNoMoreInteractions(categoryValidationAddNew);
  }

  /**
   * Trying to add category with empty name
   * Should return one error messages
   */
  @Test
  void shouldReturnErrorMsgCategoryNameCannotBeNull(){
    //given
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add("Category name cannot be null");

    //when
    when(categoryValidationAddNew.checkCategory(any())).thenReturn(errorMessages);

    //then
    assertThatIllegalArgumentException()
            .isThrownBy(()->categoryService.addCategory(categoryMapper.map(CATEGORY_WITH_EMPTY_NAME)))
            .withMessage("[Category name cannot be null]")
            .withNoCause();
  }

  @Test
  void shouldAddNewCategory() {
    when(categoryRepository.save(any())).thenReturn(CATEGORY_ENTITY);
    Category saved = categoryService.addCategory(categoryMapper.map(CATEGORY_ENTITY));

    assertThat(saved.getId()).isEqualTo(CATEGORY_ENTITY.getId());
    verify(categoryRepository).save(any());
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  void shouldChangeExistingCategory() {
    when(categoryRepository.save(any())).thenReturn(CATEGORY_ENTITY);
    Category saved = categoryService.addCategory(categoryMapper.map(CATEGORY_ENTITY));

    CATEGORY_ENTITY.setLeaf(true);
    CATEGORY_ENTITY.setName("Horrory");
    CATEGORY_ENTITY.setParentCategory(null);

    when(categoryRepository.update(any())).thenReturn(CATEGORY_ENTITY);
    Category updated = categoryService.changeCategory(saved);

    assertThat(updated.getId()).isEqualTo(CATEGORY_ENTITY.getId());
    assertThat(updated.getName()).isEqualTo(CATEGORY_ENTITY.getName());
    assertThat(updated.isLeaf()).isEqualTo(CATEGORY_ENTITY.isLeaf());
    assertThat(updated.getParentCategory()).isNull();

    verify(categoryRepository).save(any());
    verify(categoryRepository).update(any());

    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  void shouldDeleteCategory(){
    when(categoryRepository.save(any())).thenReturn(CATEGORY_ENTITY);
    Category saved = categoryService.addCategory(categoryMapper.map(CATEGORY_ENTITY));

    doNothing().when(categoryRepository).delete(any());
    categoryService.deleteCategory(saved);
    long savedId = saved.getId();

    when(categoryRepository.getById(savedId)).thenReturn(Optional.empty());
    Optional<Category> found = categoryService.findById(savedId);

    assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->found.orElseThrow(NoSuchElementException::new));

    verify(categoryRepository).save(any());
    verify(categoryRepository).delete(any());
    verify(categoryRepository).getById(savedId);

    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  void shouldFindTwoChildren(){
    //TODO: Zmień nazwę metody.
    List<CategoryEntity> returnedCategories = new ArrayList<>();
    returnedCategories.add(CATEGORY_ENTITY);

    when(categoryRepository.findAllChildren(PARENT_CATEGORY_ID)).thenReturn(returnedCategories);

    List<Category> foundedCategories = categoryService.findChildren(PARENT_CATEGORY.getId());
    assertThat(foundedCategories.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnEmptyChildrenList(){
    when(categoryRepository.save(any())).thenReturn(PARENT_CATEGORY);
    Category parentSaved = categoryService.addCategory(categoryMapper.map(PARENT_CATEGORY));

    long parentSavedId = parentSaved.getId();
    List<CategoryEntity> returnedCategories = new ArrayList<>();

    when(categoryRepository.findAllChildren(parentSavedId)).thenReturn(returnedCategories);

    List<Category> foundedCategories = categoryService.findChildren(PARENT_CATEGORY.getId());
    assertThat(foundedCategories).isEmpty();
  }

  @Test
  void shouldCountOneChildren(){
    when(categoryRepository.countChildren(PARENT_CATEGORY_ID)).thenReturn(1);
    int countedChildren = categoryService.countChildren(PARENT_CATEGORY_ID);

    assertThat(countedChildren).isEqualTo(1);
  }
}