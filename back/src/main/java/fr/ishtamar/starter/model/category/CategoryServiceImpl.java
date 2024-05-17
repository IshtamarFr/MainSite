package fr.ishtamar.starter.model.category;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;
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

    @Override
    public List<Category> getCategoriesForUser(UserInfo user) {
        return repository.findByUser(user);
    }

    @Override
    public Category getCategoryById(Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(UserInfo.class,"id",id.toString()));
    }

    @Override
    public void deleteCategory(Category category) {
        repository.delete(category);
    }

    @Override
    public Category modifyCategory(Category category, String name) throws GenericException {
        List<Category> candidate=repository.findByNameAndUser(name,category.getUser());

        if (candidate.isEmpty()) {
            category.setName(name);
            return repository.save(category);
        } else {
            throw new GenericException("This category already exists for this user");
        }
    }
}
