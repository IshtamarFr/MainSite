package fr.ishtamar.frozen.model.batch;

import fr.ishtamar.frozen.model.containertype.ContainerTypeDto;
import fr.ishtamar.frozen.model.food.FoodDto;
import fr.ishtamar.frozen.model.location.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchDto {
    private Long id;
    private Long units;
    private Long weightGrams;
    private String comment;

    private FoodDto food;
    private ContainerTypeDto containerType;
    private LocationDto location;

    private Long user_id;
}
