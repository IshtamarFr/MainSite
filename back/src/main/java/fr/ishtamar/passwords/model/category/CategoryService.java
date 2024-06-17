package fr.ishtamar.passwords.model.category;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category) throws GenericException;
    List<Category> getCategoriesForUser(UserInfo user);
    Category getCategoryById(Long id) throws EntityNotFoundException;
    void deleteCategory(Category category);
    Category modifyCategory(Category category,String name) throws GenericException;
}
