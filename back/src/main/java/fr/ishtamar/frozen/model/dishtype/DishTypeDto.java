package fr.ishtamar.frozen.model.dishtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishTypeDto {
    private Long id;
    private String name;
    private Long monthsDefault;
}
