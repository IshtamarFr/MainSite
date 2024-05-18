package fr.ishtamar.starter.model.password;

import fr.ishtamar.starter.model.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=63)
    private String siteName;

    @Size(max=255)
    private String siteAddress;

    @Size(max=127)
    private String siteLogin;

    @NotNull
    private String passwordKey;

    @NotNull
    @Size(min=4,max=6)
    private String passwordPrefix;

    @NotNull
    @Min(8)
    @Max(64)
    private Long passwordLength;

    @Size(max=500)
    private String description;

    private boolean isActive;

    @ManyToOne
    @NotNull
    @JoinColumn(name="category_id",referencedColumnName = "id")
    private Category category;
}
