package pl.sdacademy.bookstore.service.validation;

/**
 * A class that collects methods validating actions of modifying existing category
 *
 * @author Irek Marszałek
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.model.Category;

import java.util.List;

@Service
public class CategoryValidationChangeExisting {

    private final CategoryValidationAddNew categoryValidationAddNew;

    @Autowired
    public CategoryValidationChangeExisting(CategoryValidationAddNew categoryValidationAddNew) {
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
}
