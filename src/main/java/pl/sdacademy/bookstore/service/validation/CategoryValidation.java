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
import pl.sdacademy.bookstore.model.dto.Category;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryValidation {

  List<String> checkIfNewNameIsCorrect(Category category){
    List<String> errorMessages = new ArrayList<>();
    if (category == null){
      errorMessages.add("Category cannot be null");
    }else {
      if(category.getName() == null){
        errorMessages.add("Category name cannot be null");
      }else {
        if (category.getName().length() < 3) {
          errorMessages.add("Category name cannot be shorter than 3 chars");
        }
      }
    }
    return errorMessages;
  }

  List<String> checkIfParentCategoryIsCorrect(Category category){
    List<String> errorMessages = new ArrayList<>();
    if (category == null){
      errorMessages.add("Category cannot be null");
    }else {
      if(category.getParentCategory() == null){
        errorMessages.add("Category parent cannot be null");
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
