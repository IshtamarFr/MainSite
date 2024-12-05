package fr.ishtamar.frozen.model.batch;

import fr.ishtamar.frozen.model.containertype.ContainerType;
import fr.ishtamar.frozen.model.food.Food;
import fr.ishtamar.frozen.model.location.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Batch {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    private Long units;

    @Min(0)
    private Long weightGrams;

    @Length(max=500)
    private String comment;

    @ManyToOne
    @JoinColumn(name="food_id",referencedColumnName = "id")
    @NotNull
    private Food food;

    @ManyToOne
    @JoinColumn(name="containertype_id",referencedColumnName = "id")
    private ContainerType containerType;

    @ManyToOne
    @JoinColumn(name="location_id",referencedColumnName = "id")
    @NotNull
    private Location location;
}
