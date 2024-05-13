package fr.ishtamar.starter.category;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository){this.repository=repository;}

    @Override
    public Category createCategory(Category category) throws GenericException {
        List<Category> candidate=repository.findByNameAndUser(category.getName(),category.getUser());

        if (candidate.isEmpty()) {
            return repository.save(category);
        } else {
            throw new GenericException("This category already exists for this user");
        }
    }
}
