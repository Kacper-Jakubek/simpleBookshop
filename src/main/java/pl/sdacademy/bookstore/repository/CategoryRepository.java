package pl.sdacademy.bookstore.repository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.sdacademy.bookstore.db.CategoryEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Data
@Repository
public class CategoryRepository {
  private final EntityManager entityManager;

  @Autowired
  public CategoryRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public CategoryEntity save(CategoryEntity category){
    entityManager.persist(category);
    return category;
  }
}
