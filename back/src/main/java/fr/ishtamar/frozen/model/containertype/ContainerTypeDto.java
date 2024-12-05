package fr.ishtamar.frozen.model.containertype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContainerTypeDto {
    private Long id;
    private String name;
}
