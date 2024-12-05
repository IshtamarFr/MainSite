package fr.ishtamar.frozen.model.food;

import fr.ishtamar.starter.util.EntityMapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface FoodMapper extends EntityMapper<FoodDto,Food> {
    @Mappings({
            @Mapping(source="food.dishType.id",target="dishType_id"),
            @Mapping(source="food.dishType.name",target="dishType_name"),
    })
    FoodDto toDto(Food food);
}
