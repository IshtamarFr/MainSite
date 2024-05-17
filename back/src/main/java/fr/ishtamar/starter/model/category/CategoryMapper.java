package fr.ishtamar.starter.model.category;

import fr.ishtamar.starter.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public interface CategoryMapper extends EntityMapper<CategoryDto,Category> {

    @Mapping(source= "category.user.id",target="user_id")
    CategoryDto toDto(Category category);
}
