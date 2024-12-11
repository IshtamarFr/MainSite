package fr.ishtamar.frozen.model.food;

import fr.ishtamar.frozen.model.dishtype.DishType;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.standard.StdEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Food implements StdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max=64)
    private String name;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime dlc;

    @Length(max=500)
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @NotNull
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name="dishtype_id", referencedColumnName = "id")
    private DishType dishType; //Not mandatory
}
