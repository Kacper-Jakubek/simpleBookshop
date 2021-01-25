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

  /**
   * Clear category table before each test
   */
  @BeforeEach
  void clearBeforeEach(){
    categoryRepository.deleteAll();
  }

  /**
   * It inserts new category
   */
  @Test
  void shouldSaveNewCategory() {
    //given
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Fantasy");
    categoryEntity.setParentCategory(null);
    categoryEntity.setLeaf(true);

    //when
    CategoryEntity saved = categoryRepository.save(categoryEntity);
    long foundId = saved.getId();
    CategoryEntity found = categoryRepository.getById(foundId).orElse(new CategoryEntity());

    //then
    assertThat(categoryEntity.getId())
            .isEqualTo(found.getId());
    assertThat(categoryEntity.getName())
            .isEqualTo(found.getName());
    assertThat(categoryEntity.isLeaf())
            .isEqualTo(found.isLeaf());
    assertThat(found.getParentCategory())
            .isNull();
  }

  /**
   * It makes an update of existing category
   */
  @Test
  void shouldUpdateExistingCategoryWithParentCategory(){
    //given
    CategoryEntity categoryEntityParent = new CategoryEntity();
    categoryEntityParent.setName("Książki");
    categoryEntityParent.setParentCategory(null);
    categoryEntityParent.setLeaf(false);
    categoryRepository.save(categoryEntityParent);

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(true);
    categoryRepository.save(categoryEntity);
    categoryEntity.setParentCategory(categoryEntityParent);

    //when
    CategoryEntity updated = categoryRepository.update(categoryEntity);
    long foundId = updated.getId();
    CategoryEntity found = categoryRepository.getById(foundId).orElse(new CategoryEntity());

    //then
    assertThat(found.getId())
            .isEqualTo(categoryEntity.getId());
    assertThat(found.getName())
            .isEqualTo(categoryEntity.getName());
    assertThat(found.getParentCategory().getId())
            .isEqualTo(categoryEntityParent.getId());
    assertThat(found.isLeaf())
            .isEqualTo(categoryEntity.isLeaf());
  }

  /**
   * It looks for all categories
   */
  @Test
  void shouldFindTwoCategories() {
    //given
    CategoryEntity categoryEntityParent = new CategoryEntity();
    categoryEntityParent.setName("Książki");
    categoryEntityParent.setParentCategory(null);
    categoryEntityParent.setLeaf(false);
    categoryRepository.save(categoryEntityParent);

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Horror");
    categoryEntity.setLeaf(true);
    categoryRepository.save(categoryEntity);

    //when
    List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

    CategoryEntity found;
    if(categoryEntityList.get(0).getId() == categoryEntity.getId()){
      found = categoryEntityList.get(0);
    }else{
      found = categoryEntityList.get(1);
    }

    //then
    assertNotNull(categoryEntityList);
    assertThat(categoryEntityList.size())
            .isEqualTo(2);

    assertNotNull(found);
    assertEquals(categoryEntity.getId(),found.getId());
    assertEquals(categoryEntity.getName(),found.getName());
    assertEquals(categoryEntity.isLeaf(),found.isLeaf());
  }

  /**
   * It deletes category by object
   */
  @Test
  void shouldDeleteCategory() {
    //given
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Bibliografia");
    categoryEntity.setLeaf(true);
    CategoryEntity saved = categoryRepository.save(categoryEntity);

    //when
    categoryRepository.delete(saved);
    long savedId = saved.getId();
    Optional<CategoryEntity> found = categoryRepository.getById(savedId);

    //then
    assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }

  /**
   * It deletes category by id
   */
  @Test
  void shouldDeleteCategoryById() {
    //given
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Bibliografia");
    categoryEntity.setLeaf(true);
    CategoryEntity saved = categoryRepository.save(categoryEntity);
    long savedId = saved.getId();

    //when
    categoryRepository.deleteById(savedId);
    Optional<CategoryEntity> found = categoryRepository.getById(savedId);

    //then
    assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(()->found.orElseThrow(NoSuchElementException::new));
  }


  /**
   * It looks for a children of parent category
   */
  @Test
  void shouldFindOneChild() {
    //given
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

    //when
    List<CategoryEntity> children = categoryRepository.findAllChildren(parentSavedId);

    //then
    assertThat(children.size())
            .isEqualTo(1);
  }

  /**
   * It looks for a children of parent category
   */
  @Test
  void shouldReturnEmptyChildrenList() {
    //given
    CategoryEntity parentCategoryEntity = new CategoryEntity();
    parentCategoryEntity.setName("Książki");
    parentCategoryEntity.setLeaf(true);
    CategoryEntity parentSaved = categoryRepository.save(parentCategoryEntity);
    long parentSavedId = parentSaved.getId();

    //when
    List<CategoryEntity> children = categoryRepository.findAllChildren(parentSavedId);

    //then
    assertThat(children).isEmpty();
  }

  /**
   * It counts children of parent category
   */
  @Test
  void shouldCountZeroChildren(){
    //given, when
    int countedChildren = categoryRepository.countChildren(1);

    //then
    assertThat(countedChildren)
            .isZero();
  }

  /**
   * It counts children of parent category
   */
  @Test
  void shouldCountOneChild(){
    //given
    CategoryEntity parentCategoryEntity = new CategoryEntity();
    parentCategoryEntity.setName("Root");
    parentCategoryEntity.setLeaf(false);
    parentCategoryEntity.setParentCategory(null);
    CategoryEntity parentSaved = categoryRepository.save(parentCategoryEntity);

    //when
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setName("Fantasy");
    categoryEntity.setLeaf(true);
    categoryEntity.setParentCategory(parentSaved);
    categoryRepository.save(categoryEntity);
    long parentCategoryId = parentCategoryEntity.getId();

    //then
    int countedChildren = categoryRepository.countChildren(parentCategoryId);
    assertThat(countedChildren)
            .isEqualTo(1);
  }
}