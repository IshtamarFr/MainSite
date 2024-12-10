package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.util.StdEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name","user_id"})
})
public class Location implements StdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max=32)
    private String name;

    @Getter
    @Length(max=128)
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    @NotNull
    private UserInfo user;
}
