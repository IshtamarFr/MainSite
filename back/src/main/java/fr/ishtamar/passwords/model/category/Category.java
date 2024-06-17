package fr.ishtamar.passwords.model.category;

import fr.ishtamar.starter.model.user.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=63)
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserInfo user;
}
