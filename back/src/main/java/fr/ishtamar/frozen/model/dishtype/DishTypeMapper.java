package fr.ishtamar.frozen.model.dishtype;

import fr.ishtamar.starter.util.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public interface DishTypeMapper extends EntityMapper<DishTypeDto,DishType> {
}
