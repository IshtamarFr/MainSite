package fr.ishtamar.passwords.model.password;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDto {
    private Long id;
    private String siteName;
    private String siteAddress;
    private String siteLogin;
    private String description;
    private boolean active;
    private Long category_id;
    private Long user_id;
    private String passwordPrefix;
    private Long passwordLength;
}
