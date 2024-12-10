package fr.ishtamar.passwords.model.category;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityService;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends StdEntityServiceImpl<Category> implements CategoryService {

    public CategoryServiceImpl(StdEntityRepository<Category> repository) {
        super(repository);
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
