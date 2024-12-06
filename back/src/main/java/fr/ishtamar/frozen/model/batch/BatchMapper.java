package fr.ishtamar.frozen.model.batch;

import fr.ishtamar.frozen.model.containertype.ContainerTypeMapper;
import fr.ishtamar.frozen.model.food.FoodMapper;
import fr.ishtamar.frozen.model.location.LocationMapper;
import fr.ishtamar.starter.util.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring", uses={FoodMapper.class, ContainerTypeMapper.class, LocationMapper.class})
public interface BatchMapper extends EntityMapper<BatchDto,Batch> {
}
