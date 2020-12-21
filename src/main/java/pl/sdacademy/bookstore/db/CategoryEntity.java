package pl.sdacademy.bookstore.db;

/**
 * A class that represents Category entity
 *
 * <p>An <code>Category</code> instance is supposed to be a representation
 * of table that will be created in database. Should complies with entries in change-log.xml
 *
 * @author Irek Marszałek
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "category")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  @ManyToOne
  private CategoryEntity parentCategory;
  boolean leaf;

  public CategoryEntity(long id, String name, CategoryEntity parentCategory, boolean leaf) {
    this.id = id;
    this.name = name;
    this.parentCategory = parentCategory;
    this.leaf = leaf;
  }

  public CategoryEntity() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CategoryEntity getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(CategoryEntity parentCategory) {
    this.parentCategory = parentCategory;
  }

  public boolean isLeaf() {
    return leaf;
  }

  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }
}
