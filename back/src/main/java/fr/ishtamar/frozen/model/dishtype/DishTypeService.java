package fr.ishtamar.frozen.model.dishtype;

import fr.ishtamar.starter.standard.StdEntityService;

public interface DishTypeService extends StdEntityService<DishType> {
    DishType modifyDishType(DishType dishType,String name,Long monthsDefault);
}
