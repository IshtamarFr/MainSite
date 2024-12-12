package fr.ishtamar.frozen.model.dishtype;

import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;

public class DishTypeServiceImpl extends StdEntityServiceImpl<DishType> implements DishTypeService  {
    public DishTypeServiceImpl(StdEntityRepository<DishType> repository) {
        super(repository);
    }
}
