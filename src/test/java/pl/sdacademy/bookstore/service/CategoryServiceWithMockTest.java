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
          , false);

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

  private static final long CATEGORY_WITH_FALSE_LEAF_ID = 4;
  private static final String CATEGORY_WITH_FALSE_LEAF_NAME = "Informatyczne";
  private static final CategoryEntity CATEGORY_WITH_FALSE_LEAF_PARENT_CATEGORY = null;
  private static final CategoryEntity CATEGORY_WITH_FALSE_LEAF = new CategoryEntity(CATEGORY_WITH_FALSE_LEAF_ID
          , CATEGORY_WITH_FALSE_LEAF_NAME
          , CATEGORY_WITH_FALSE_LEAF_PARENT_CATEGORY
          ,false);


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

  /**
   * Trying to add category with false leaf
   * Should return one error messages
   */
  @Test
  void shouldReturnErrorMsgCategoryLeafCannotBeFalse(){
    ///given
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add("Category leaf cannot be false");

    //when
    when(categoryValidationAddNew.checkCategory(any())).thenReturn(errorMessages);

    // then
    assertThatIllegalArgumentException()
            .isThrownBy(()->categoryService.addCategory(categoryMapper.map(CATEGORY_WITH_FALSE_LEAF)))
            .withMessage("[Category leaf cannot be false]")
            .withNoCause();
  }

  /**
   * Creates new category with parent category null
   */
  @Test
  void shouldAddNewCategory() {
    //when
    when(categoryRepository.save(any())).thenReturn(PARENT_CATEGORY);
    Category saved = categoryService.addCategory(categoryMapper.map(PARENT_CATEGORY));

    long savedId = saved.getId();
    when(categoryRepository.getById(savedId)).thenReturn(Optional.of(PARENT_CATEGORY));
    Category found = categoryService.findById(savedId).orElse(null);

    //then
    assertThat(found).isNotNull();
    assertThat(found.getId()).isEqualTo(PARENT_CATEGORY.getId());
    assertThat(found.getName()).isEqualTo(PARENT_CATEGORY.getName());
    assertThat(found.getParentCategory()).isNull();
    assertThat(found.isLeaf()).isFalse();
    verify(categoryRepository).save(any());
    verify(categoryRepository).getById(savedId);
    verifyNoMoreInteractions(categoryRepository);
  }

  /**
   * Creates new category with parent category
   * While adding it should set parent leaf to false
   */
  @Test
  void shouldAddNewCategoryAndSetUpParentCategoryLeafToFalse() {
    //when
    when(categoryRepository.save(any())).thenReturn(PARENT_CATEGORY);
    Category parentCategorySaved = categoryService.addCategory(categoryMapper.map(PARENT_CATEGORY));
    long parentCategorySavedId = parentCategorySaved.getId();

    when(categoryRepository.getById(parentCategorySavedId)).thenReturn(Optional.of(PARENT_CATEGORY));
    Category foundedParentCategory = categoryService.findById(parentCategorySavedId).orElse(null);

    when(categoryRepository.save(any())).thenReturn(CATEGORY_ENTITY);
    Category saved = categoryService.addCategory(categoryMapper.map(CATEGORY_ENTITY));
    long savedID = saved.getId();

    when(categoryRepository.getById(savedID)).thenReturn(Optional.of(CATEGORY_ENTITY));
    Category found = categoryService.findById(savedID).orElse(null);

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