package fr.ishtamar.starter.model.password;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePasswordRequest {
    @NotNull
    @Size(max=63)
    private String siteName;

    @Size(max=255)
    private String siteAddress;

    @Size(max=127)
    private String siteLogin;

    @Size(min=4,max=4)
    private String passwordPrefix; //Auto set up if empty

    @Min(8)
    @Max(64)
    private Long passwordLength; //Auto set up if empty

    @Size(max=500)
    private String description;
}
