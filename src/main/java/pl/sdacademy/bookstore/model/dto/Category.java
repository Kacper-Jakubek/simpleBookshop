package pl.sdacademy.bookstore.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

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

  @NotNull(message = "Category name cannot be empty")
  @Length(min =3, message = "Category name cannot be shorter than 3 chars")
  private String name;

  @NotNull(message = "Parent category cannot be null")
  private Category parentCategory;

  @NotNull(message = "It must be marked if category is a leaf or not")
  boolean leaf;

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
