package fr.ishtamar.frozen.model.batch;

import fr.ishtamar.frozen.model.food.FoodMapper;
import fr.ishtamar.starter.util.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses= FoodMapper.class, componentModel = "Spring")
public interface BatchService extends EntityMapper<BatchDto,Batch> {
}
