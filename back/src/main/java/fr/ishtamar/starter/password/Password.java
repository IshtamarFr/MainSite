package fr.ishtamar.starter.password;

import fr.ishtamar.starter.user.UserInfo;
import jakarta.persistence.*;
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
    private Long passwordLength;

    @Size(max=500)
    private String description;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserInfo user;
}
