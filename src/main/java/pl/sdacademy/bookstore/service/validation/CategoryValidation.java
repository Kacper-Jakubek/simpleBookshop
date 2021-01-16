package pl.sdacademy.bookstore.service.validation;

/**
 * A class that that collects methods validating actions performed on Category
 *
 * <p>An <code>CategoryValidation</code> instance is supposed to be a set
 * of methods created to provide all validations methods checking if called action can be performed
 *
 * @author Irek Marszałek
 */

import org.springframework.stereotype.Component;
import pl.sdacademy.bookstore.model.Category;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryValidation {

  private static final String CATEGORY_CANNOT_BE_NULL = "Category cannot be null";
  private static final String CATEGORY_NAME_CANNOT_BE_NULL = "Category name cannot be null";
  private static final String CATEGORY_NAME_CANNOT_BE_SHORTER_THAN_3_CHARS = "Category name cannot be shorter than 3 chars";
  private static final String CATEGORY_PARENT_CANNOT_BE_NULL = "Category parent cannot be null";

  List<String> checkIfNewNameIsCorrect(Category category){
    List<String> errorMessages = new ArrayList<>();
    if (category == null){
      errorMessages.add(CATEGORY_CANNOT_BE_NULL);
    }else {
      if(category.getName() == null){
        errorMessages.add(CATEGORY_NAME_CANNOT_BE_NULL);
      }else {
        if (category.getName().length() < 3) {
          errorMessages.add(CATEGORY_NAME_CANNOT_BE_SHORTER_THAN_3_CHARS);
        }
      }
    }
    return errorMessages;
  }

  List<String> checkIfParentCategoryIsCorrect(Category category){
    List<String> errorMessages = new ArrayList<>();
    if (category == null){
      errorMessages.add(CATEGORY_CANNOT_BE_NULL);
    }else {
      if(category.getParentCategory() == null){
        errorMessages.add(CATEGORY_PARENT_CANNOT_BE_NULL);
      }
    }
    return errorMessages;
  }

  List<String > checkIfCanBeCreated(Category category){
    List<String> errorMessages = new ArrayList<>();
    if(!checkIfNewNameIsCorrect(category).isEmpty()){
      errorMessages.addAll(checkIfNewNameIsCorrect(category));
    }
    if(!checkIfParentCategoryIsCorrect(category).isEmpty()){
      errorMessages.addAll(checkIfParentCategoryIsCorrect(category));
    }
    return errorMessages;
  }
}
