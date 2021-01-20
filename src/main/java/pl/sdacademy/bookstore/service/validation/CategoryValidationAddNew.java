package pl.sdacademy.bookstore.service.validation;

/**
 * A class that collects methods validating actions of adding new category
 *
 * @author Irek Marszałek
 */

import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.model.Category;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryValidationAddNew {

  public CategoryValidationAddNew() {
  }

  private static final String ERROR_MSG_CATEGORY_CANNOT_BE_NULL = "Category cannot be null";
  private static final String ERROR_MSG_CATEGORY_NAME_CANNOT_BE_NULL = "Category name cannot be null";
  private static final String ERROR_MSG_CATEGORY_NAME_CANNOT_BE_SHORTER_THAN_3_CHARS = "Category name cannot be shorter than 3 chars";
  private static final String ERROR_MSG_CATEGORY_LEAF_CANNOT_BE_FALSE = "Category leaf cannot be false";

  /**
   * It validates if name of a category if proper.
   *
   * @param category DTO instance
   * @return list of error messages
   *
   * Name cannot be null
   * Name cannot be shorter than 3 chars
   */
  public List<String> checkName(Category category){
    List<String> errorMessages = new ArrayList<>();
    if (category == null){
      errorMessages.add(ERROR_MSG_CATEGORY_CANNOT_BE_NULL);
    }else {
      if(category.getName() == null){
        errorMessages.add(ERROR_MSG_CATEGORY_NAME_CANNOT_BE_NULL);
      }else {
        if (category.getName().length() < 3) {
          errorMessages.add(ERROR_MSG_CATEGORY_NAME_CANNOT_BE_SHORTER_THAN_3_CHARS);
        }
      }
    }
    return errorMessages;
  }

  /**
   * It validates if leaf is properly set up.
   *
   * @param category DTO instance
   * @return list of error messages
   *
   * Leaf cannot be null
   * Leaf must be true
   */
  public List<String> checkLeaf(Category category){
    List<String> errorMessages = new ArrayList<>();
    if(category == null){
      errorMessages.add(ERROR_MSG_CATEGORY_CANNOT_BE_NULL);
    }else{
      if(!category.isLeaf()){
        errorMessages.add(ERROR_MSG_CATEGORY_LEAF_CANNOT_BE_FALSE);
      }
    }
    return errorMessages;
  }

  /**
   * It validates if category meets all requirements to be added.
   *
   * @param category DTO instance
   * @return list of error messages
   *
   * It checks name and leaf
   */
  public List<String > checkCategory(Category category){
    List<String> errorMessages = new ArrayList<>();

    if(!checkName(category).isEmpty()){
      errorMessages.addAll(checkName(category));
    }

    if(!checkLeaf(category).isEmpty()) {
      errorMessages.addAll(checkLeaf(category));
    }

    return errorMessages;
  }
}
