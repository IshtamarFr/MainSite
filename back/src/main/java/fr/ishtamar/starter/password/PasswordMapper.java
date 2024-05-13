package fr.ishtamar.starter.password;

import fr.ishtamar.starter.util.EntityMapper;
import fr.ishtamar.starter.user.UserInfoServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public interface PasswordMapper extends EntityMapper<PasswordDto, Password> {

    @Mappings({
            @Mapping(source= "password.category.id",target="category_id"),
            @Mapping(source= "password.category.user.id",target="user_id")
    })
    PasswordDto toDto(Password password);
}
