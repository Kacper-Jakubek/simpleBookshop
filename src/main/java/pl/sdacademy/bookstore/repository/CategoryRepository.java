package pl.sdacademy.bookstore.repository;

/**
 * A class that that collects methods that allows to access CategoryEntity
 *
 * <p>An <code>CategoryRepository</code> instance is supposed to be a set
 * of methods created to provide an access to CategoryEntity
 *
 * @author Irek Marszałek
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.sdacademy.bookstore.db.CategoryEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {
  private final EntityManager entityManager;

  @Autowired
  public CategoryRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public CategoryEntity save(CategoryEntity categoryEntity){
    entityManager.persist(categoryEntity);
    return categoryEntity;
  }

  @Transactional
  public CategoryEntity update(CategoryEntity categoryEntity){
    entityManager.merge(categoryEntity);
    return categoryEntity;
  }

  @Transactional
  public Optional<CategoryEntity> getById(long id){
    return Optional.ofNullable(entityManager.find(CategoryEntity.class, id));
  }

  @Transactional
  public List<CategoryEntity> findAll(){
    return entityManager.createQuery("select c from category c", CategoryEntity.class).getResultList();
  }

  @Transactional
  public void delete(CategoryEntity categoryEntity){
    entityManager.remove(entityManager.contains(categoryEntity)?categoryEntity:entityManager.merge(categoryEntity));
  }

  @Transactional
  public void deleteById(long id){
    entityManager.createQuery("delete from category c where c.id = :id")
            .setParameter("id", id)
            .executeUpdate();
  }

  @Transactional
  public void deleteAll(){
    entityManager.createQuery("delete from category c")
            .executeUpdate();
  }

  @Transactional
  public List<CategoryEntity> findAllChildCategories(long id){
    return entityManager
            .createQuery("select c from category c where c.parentCategory.id = :id", CategoryEntity.class)
            .setParameter("id", id)
            .getResultList();
  }
}
