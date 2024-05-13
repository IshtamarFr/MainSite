package fr.ishtamar.starter.category;

import fr.ishtamar.starter.exceptionhandler.GenericException;

public interface CategoryService {
    Category createCategory(Category category) throws GenericException;
}
