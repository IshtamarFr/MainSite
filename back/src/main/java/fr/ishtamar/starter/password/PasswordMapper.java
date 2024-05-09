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
public abstract class PasswordMapper implements EntityMapper<PasswordDto, Password> {

    @Autowired
    UserInfoServiceImpl userInfoService;

    @Mappings({
            @Mapping(target="user", expression="java(this.userInfoService.getUserById(passwordDto.getUser_id()))")
    })
    public abstract Password toEntity(PasswordDto passwordDto);

    @Mappings({
            @Mapping(source= "password.user.id",target="user_id")
    })
    public abstract PasswordDto toDto(Password password);
}
