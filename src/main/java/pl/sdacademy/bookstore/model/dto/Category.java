package pl.sdacademy.bookstore.model.dto;
/* By IM */
/*Books category dto*/

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
