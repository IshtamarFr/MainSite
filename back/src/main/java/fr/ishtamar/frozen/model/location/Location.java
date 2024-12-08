package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.model.user.UserInfo;
import jakarta.persistence.*;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name","user_id"})
})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max=32)
    private String name;

    @Length(max=128)
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserInfo user;
}
