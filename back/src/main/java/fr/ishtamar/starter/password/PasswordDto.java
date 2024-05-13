package fr.ishtamar.starter.password;

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
    private boolean isActive;
    private Long category_id;
    private Long user_id;
}
