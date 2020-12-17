package pl.sdacademy.bookstore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.sdacademy.bookstore.db.CategoryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"parentCategory"})
public class Category {
  private long id;
  private String name;
  private CategoryEntity parentCategory;
  boolean leaf;
}
