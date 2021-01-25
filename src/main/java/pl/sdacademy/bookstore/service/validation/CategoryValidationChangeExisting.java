package pl.sdacademy.bookstore.service.validation;

/**
 * A class that collects methods validating actions of modifying existing category
 *
 * @author Irek Marszałek
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.model.Category;
import pl.sdacademy.bookstore.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryValidationChangeExisting {

    private static final String ERROR_MSG_PARENT_CATEGORY_CANNOT_BE_CHANGE_TO_CHILDREN = "Parent category cannot be change to children";
    private static final String ERROR_MSG_CATEGORY_CANNOT_BE_NULL = "Category cannot be null";

    private final CategoryService categoryService;
    private final CategoryValidationAddNew categoryValidationAddNew;

    @Autowired
    public CategoryValidationChangeExisting(CategoryService categoryService, CategoryValidationAddNew categoryValidationAddNew) {
        this.categoryService = categoryService;
        this.categoryValidationAddNew = categoryValidationAddNew;
    }

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
        return categoryValidationAddNew.checkName(category);
    }

    /**
     * It validates if parent category is properly changed.
     *
     * @param category DTO instance
     * @return list of error messages
     *
     * Parent category cannot be changed to category children
     */
    public List<String> checkParentCategory(Category category){
        List<String> errorMessages = new ArrayList<>();

        if(category==null){
            errorMessages.add(ERROR_MSG_CATEGORY_CANNOT_BE_NULL);
        }else{
            //Cannot change parent category to yourself children
            List<Category> children = categoryService.findChildren(category.getId());
            children.stream()
                    .filter(child -> child.getId() == category.getParentCategory().getId())
                    .collect(Collectors.toList());

            if(!children.isEmpty()){
                errorMessages.add(ERROR_MSG_PARENT_CATEGORY_CANNOT_BE_CHANGE_TO_CHILDREN);
            }
        }

        return errorMessages;
    }
}
