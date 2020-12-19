package pl.sdacademy.bookstore.repository;
/* By IM */
/*Category repository tests*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.db.CategoryEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  void clearBeforeEach(){
    categoryRepository.deleteAll();
  }

  @Test
  void shouldSaveNewCategory() {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Fantasy");
    categoryEntity.setLeaf(false);

    CategoryEntity saved = categoryRepository.save(categoryEntity);

    long foundId = saved.getId();

    CategoryEntity found = categoryRepository.getById(foundId).orElse(new CategoryEntity());
    assertThat(categoryEntity.getId()).isEqualTo(found.getId());
    assertThat(categoryEntity.getName()).isEqualTo(found.getName());
    assertThat(categoryEntity.isLeaf()).isEqualTo(found.isLeaf());
    assertThat(categoryEntity.getParentCategory()).isNull();
  }

  @Test
  void shouldUpdateExistingCategoryWithParentCategory(){
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(false);
    categoryRepository.save(categoryEntity);

    CategoryEntity categoryEntityParent = new CategoryEntity();
    categoryEntityParent.setName("Książki");
    categoryEntityParent.setLeaf(false);
    categoryRepository.save(categoryEntityParent);

    categoryEntity.setParentCategory(categoryEntityParent);
    categoryRepository.update(categoryEntity);

    assertThat(categoryEntity.getParentCategory().getId()).isEqualTo(categoryEntityParent.getId());
    assertThat(categoryEntity.getParentCategory().getName()).isEqualTo(categoryEntityParent.getName());
    assertThat(categoryEntity.getParentCategory().isLeaf()).isEqualTo(categoryEntityParent.isLeaf());

  }

  @Test
  void shouldUpdateExistingCategoryWithHimSelf(){
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(false);
    categoryRepository.save(categoryEntity);

    categoryEntity.setParentCategory(categoryEntity);
    categoryRepository.update(categoryEntity);

    assertThat(categoryEntity.getParentCategory().getId()).isEqualTo(categoryEntity.getId());
    assertThat(categoryEntity.getParentCategory().getName()).isEqualTo(categoryEntity.getName());
    assertThat(categoryEntity.getParentCategory().isLeaf()).isEqualTo(categoryEntity.isLeaf());
  }

  @Test
  void shouldFindTwoCategories() {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(false);
    categoryRepository.save(categoryEntity);

    CategoryEntity categoryEntityParent = new CategoryEntity();
    categoryEntityParent.setName("Książki");
    categoryEntityParent.setLeaf(false);
    categoryRepository.save(categoryEntityParent);

    List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

    assertNotNull(categoryEntityList);
    assertThat(categoryEntityList.size()).isEqualTo(2);

    CategoryEntity found;
    if(categoryEntityList.get(0).getId() == categoryEntity.getId()){
      found = categoryEntityList.get(0);
    }else{
      found = categoryEntityList.get(1);
    }

    assertNotNull(found);
    assertEquals(categoryEntity.getId(),found.getId());
    assertEquals(categoryEntity.getName(),found.getName());
    assertEquals(categoryEntity.isLeaf(),found.isLeaf());
  }

  @Test
  void shouldDeleteCategory() {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Bibliografia");
    categoryEntity.setLeaf(false);
    CategoryEntity saved = categoryRepository.save(categoryEntity);

    categoryRepository.delete(saved);
    long savedId = saved.getId();

    Optional<CategoryEntity> found = categoryRepository.getById(savedId);

    assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }

  @Test
  void shouldDeleteCategoryById() {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Bibliografia");
    categoryEntity.setLeaf(false);
    CategoryEntity saved = categoryRepository.save(categoryEntity);

    long savedId = saved.getId();
    Optional<CategoryEntity> found = categoryRepository.getById(savedId);

    assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }
}