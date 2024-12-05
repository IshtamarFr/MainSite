package fr.ishtamar.frozen.model.food;

import fr.ishtamar.frozen.model.dishtype.DishTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime dlc;
    private String description;
    private DishTypeDto dishType;
}