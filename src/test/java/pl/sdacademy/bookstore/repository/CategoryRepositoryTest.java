package pl.sdacademy.bookstore.repository;

/**
 * A class that perform integration test of CategoryRepository. It raise Spring and JPA.
 *
 * <p>An <code>CategoryRepositoryTest</code> check connection with H2 database
 * and tests all method being in CategoryRepository
 *
 * @author Irek Marszałek
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sdacademy.bookstore.db.CategoryEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    CategoryEntity categoryEntityParent = new CategoryEntity();
    categoryEntityParent.setName("Książki");
    categoryEntityParent.setLeaf(false);
    categoryRepository.save(categoryEntityParent);

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(false);
    categoryRepository.save(categoryEntity);

    categoryEntity.setParentCategory(categoryEntityParent);
    categoryRepository.update(categoryEntity);

    assertThat(categoryEntity.getParentCategory().getId()).isEqualTo(categoryEntityParent.getId());
    assertThat(categoryEntity.getParentCategory().getName()).isEqualTo(categoryEntityParent.getName());
    assertThat(categoryEntity.getParentCategory().isLeaf()).isEqualTo(categoryEntityParent.isLeaf());
  }

  @Test
  void shouldFindTwoCategories() {
    CategoryEntity categoryEntityParent = new CategoryEntity();
    categoryEntityParent.setName("Książki");
    categoryEntityParent.setLeaf(false);
    categoryRepository.save(categoryEntityParent);

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(true);
    categoryRepository.save(categoryEntity);

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

    assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }

  @Test
  void shouldDeleteCategoryById() {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Bibliografia");
    categoryEntity.setLeaf(false);
    CategoryEntity saved = categoryRepository.save(categoryEntity);

    long savedId = saved.getId();
    categoryRepository.deleteById(savedId);
    Optional<CategoryEntity> found = categoryRepository.getById(savedId);

    assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }

  @Test
  void shouldFindOneChild() {
    CategoryEntity parentCategoryEntity = new CategoryEntity();
    parentCategoryEntity.setName("Książki");
    parentCategoryEntity.setLeaf(false);
    CategoryEntity parentSaved = categoryRepository.save(parentCategoryEntity);

    long parentSavedId = parentSaved.getId();

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setParentCategory(parentSaved);
    categoryEntity.setLeaf(true);
    categoryRepository.save(categoryEntity);
    List<CategoryEntity> children = categoryRepository.findAllChildren(parentSavedId);

    assertThat(children.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnEmptyChildrenList() {
    CategoryEntity parentCategoryEntity = new CategoryEntity();
    parentCategoryEntity.setName("Książki");
    parentCategoryEntity.setLeaf(false);
    CategoryEntity parentSaved = categoryRepository.save(parentCategoryEntity);

    long parentSavedId = parentSaved.getId();
    List<CategoryEntity> children = categoryRepository.findAllChildren(parentSavedId);

    assertThat(children).isEmpty();
  }

  @Test
  void shouldCountZeroChildren(){
    int countedChildren = categoryRepository.countChildren(1);
    assertThat(countedChildren).isZero();
  }

  @Test
  void shouldCountOneChild(){
    CategoryEntity parentCategoryEntity = new CategoryEntity();
    parentCategoryEntity.setName("Root");
    parentCategoryEntity.setLeaf(false);
    CategoryEntity parentSaved = categoryRepository.save(parentCategoryEntity);

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Fantasy");
    categoryEntity.setLeaf(true);
    categoryEntity.setParentCategory(parentSaved);
    categoryRepository.save(categoryEntity);

    long parentCategoryId = parentCategoryEntity.getId();

    int countedChildren = categoryRepository.countChildren(parentCategoryId);
    assertThat(countedChildren).isEqualTo(1);
  }
}