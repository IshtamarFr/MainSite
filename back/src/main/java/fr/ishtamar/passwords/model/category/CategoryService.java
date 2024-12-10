package fr.ishtamar.passwords.model.category;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.standard.StdEntityService;

public interface CategoryService extends StdEntityService<Category> {
    Category modifyCategory(Category category,String name) throws GenericException;
}
