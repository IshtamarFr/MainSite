package fr.ishtamar.frozen.model.containertype;

import fr.ishtamar.starter.util.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public interface ContainerTypeMapper extends EntityMapper<ContainerTypeDto,ContainerType> {
}
