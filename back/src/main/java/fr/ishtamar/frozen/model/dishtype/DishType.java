package fr.ishtamar.frozen.model.dishtype;

import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.standard.StdEntity;
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
public class DishType implements StdEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Length(max=32)
    private String name;

    @Min(0)
    private Long monthsDefault; // 0 or null will be used for a forever

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    @NotNull
    private UserInfo user;
}
