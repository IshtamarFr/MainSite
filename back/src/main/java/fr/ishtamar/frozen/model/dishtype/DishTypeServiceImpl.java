package fr.ishtamar.frozen.model.dishtype;

import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishTypeServiceImpl extends StdEntityServiceImpl<DishType> implements DishTypeService  {
    public DishTypeServiceImpl(StdEntityRepository<DishType> repository) {
        super(repository);
    }
}
