package pl.sdacademy.bookstore.model.dto;

/**
 * A class that represents DTO for category from db.CategoryEntity
 *
 * <p>An <code>Category</code> instance is supposed to be a copy
 * of <code>CategoryEntity</code> and do not contain entity annotation
 * BTW. Kiedyś się nauczę pisać Javadoc, ale to nie teraz ;)
 *
 * @see pl.sdacademy.bookstore.db.CategoryEntity
 *
 * @author Irek Marszałek
 */

public class Category {
  private long id;
  private String name;
  private Category parentCategory;
  boolean leaf;

  public Category(long id, String name, Category parentCategory, boolean leaf) {
    this.id = id;
    this.name = name;
    this.parentCategory = parentCategory;
    this.leaf = leaf;
  }

  public Category() {
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

  public Category getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public boolean isLeaf() {
    return leaf;
  }

  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }
}
