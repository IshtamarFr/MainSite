package fr.ishtamar.frozen.model.batch;

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

    private Long food_id;
    private Long containerType_id;
    private Long location_id;
    private Long user_id;
}
