package pl.sdacademy.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sdacademy.bookstore.db.CategoryEntity;
import pl.sdacademy.bookstore.model.dto.Category;
import pl.sdacademy.bookstore.model.mapper.CategoryMapper;
import pl.sdacademy.bookstore.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryServiceWithMockTest {
  private static final long PARENT_CATEGORY_ID = 2;
  private static final String PARENT_CATEGORY_NAME = "Książki";
  private static final CategoryEntity PARENT_CATEGORY_PARENT = null;
  private static final boolean PARENT_LEAF = true;

  private static final CategoryEntity PARENT_CATEGORY = new CategoryEntity(PARENT_CATEGORY_ID
          , PARENT_CATEGORY_NAME
          , PARENT_CATEGORY_PARENT
          , PARENT_LEAF);

  private static final long CATEGORY_ID = 1;
  private static final String CATEGORY_NAME = "Książki";
  private static final CategoryEntity CATEGORY_PARENT = PARENT_CATEGORY;
  private static final boolean CATEGORY_LEAF = true;
  
  private static final CategoryEntity CATEGORY_ENTITY = new CategoryEntity(CATEGORY_ID
          , CATEGORY_NAME
          , CATEGORY_PARENT
          , CATEGORY_LEAF);

  CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

  @Mock
  CategoryRepository categoryRepository;

  @InjectMocks
  CategoryService categoryService;

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
    List<CategoryEntity> returnedCategories = new ArrayList<>();
    returnedCategories.add(CATEGORY_ENTITY);
    returnedCategories.add(PARENT_CATEGORY);

    when(categoryRepository.findAllChildCategories(PARENT_CATEGORY_ID)).thenReturn(returnedCategories);

    List<Category> foundedCategories = categoryService.findChildren(PARENT_CATEGORY.getId());
    assertThat(foundedCategories.size()).isEqualTo(2);
  }

  @Test
  void shouldReturnEmptyChildrenList(){
    when(categoryRepository.save(any())).thenReturn(PARENT_CATEGORY);
    Category parentSaved = categoryService.addCategory(categoryMapper.map(PARENT_CATEGORY));

    long parentSavedId = parentSaved.getId();
    List<CategoryEntity> returnedCategories = new ArrayList<>();

    when(categoryRepository.findAllChildCategories(parentSavedId)).thenReturn(returnedCategories);

    List<Category> foundedCategories = categoryService.findChildren(PARENT_CATEGORY.getId());
    assertThat(foundedCategories).isEmpty();
  }
}