package fr.ishtamar.starter.password;

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
public class PasswordDto {
    private Long id;

    @NotNull
    @Size(max=63)
    private String name;

    @NotNull
    private Long user_id;
}
