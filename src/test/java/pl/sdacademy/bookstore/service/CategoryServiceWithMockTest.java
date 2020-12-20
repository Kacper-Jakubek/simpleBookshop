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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryServiceWithMockTest {
  private static final long PARENT_CATEGORY_ID = 2;
  private static final String PARENT_CATEGORY_NAME = "Książki";
  private static final CategoryEntity PARENT_CATEGORY_PARENT = null;
  private static final boolean PARENT_LEAF = true;

  private static final CategoryEntity PARENT_CATEGORY = new CategoryEntity(PARENT_CATEGORY_ID, PARENT_CATEGORY_NAME, PARENT_CATEGORY_PARENT, PARENT_LEAF);

  private static final long CATEGORY_ID = 1;
  private static final String CATEGORY_NAME = "Książki";
  private static final CategoryEntity CATEGORY_PARENT = PARENT_CATEGORY;
  private static final boolean CATEGORY_LEAF = true;
  
  private static final CategoryEntity CATEGORY_ENTITY = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME, CATEGORY_PARENT, CATEGORY_LEAF);

  CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

  @Mock
  CategoryRepository categoryRepository;

  @InjectMocks
  CategoryService categoryService;

  @Test
  void shouldAddNewCategory() {
    when(categoryRepository.save(CATEGORY_ENTITY)).thenReturn(CATEGORY_ENTITY);

    Category saved = categoryService.addCategory(categoryMapper.map(CATEGORY_ENTITY));
    assertThat(saved.getId()).isEqualTo(CATEGORY_ENTITY.getId());
  }
}