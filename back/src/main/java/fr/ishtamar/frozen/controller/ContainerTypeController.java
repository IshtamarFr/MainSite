package fr.ishtamar.frozen.controller;

import fr.ishtamar.frozen.model.containertype.ContainerType;
import fr.ishtamar.frozen.model.containertype.ContainerTypeDto;
import fr.ishtamar.frozen.model.containertype.ContainerTypeMapper;
import fr.ishtamar.frozen.model.containertype.ContainerTypeService;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.security.JwtService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/frozen/container")
public class ContainerTypeController {
    private final ContainerTypeMapper containerTypeMapper;
    private final JwtService jwtService;
    private final ContainerTypeService containerTypeService;

    public ContainerTypeController(ContainerTypeMapper containerTypeMapper, JwtService jwtService, ContainerTypeService containerTypeService) {
        this.containerTypeMapper = containerTypeMapper;
        this.jwtService = jwtService;
        this.containerTypeService = containerTypeService;
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public ContainerTypeDto createContainerType(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestParam @NotNull @Size(max=8) String name) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        ContainerType containerType= ContainerType.builder()
                .name(name)
                .user(user)
                .build();

        return containerTypeMapper.toDto(containerTypeService.createEntity(containerType));
    }

    @GetMapping("")
    @Secured("ROLE_USER")
    public List<ContainerTypeDto> getContainerTypesForUser(
            @RequestHeader(value="Authorization",required=false) String jwt) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        return containerTypeMapper.toDto(containerTypeService.getEntitiesForUser(user));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public String deleteContainerType(@RequestHeader(value="Authorization",required=false) String jwt,@PathVariable Long id)
            throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        ContainerType containerType=containerTypeService.getEntityById(id);

        if (Objects.equals(containerType.getUser(),user)) {
            containerTypeService.deleteEntity(containerType);
            return "This containerType was successfully deleted";
        } else {
            throw new GenericException("You are not allowed to delete this resource");
        }
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public ContainerTypeDto modifyContainerType(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable Long id,
            @RequestParam @NotNull @Size(max=8) String name
    ) throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        ContainerType containerType=containerTypeService.getEntityById(id);

        if (Objects.equals(containerType.getUser(),user)) {
            return containerTypeMapper.toDto(containerTypeService.modifyContainerType(containerType,name));
        } else {
            throw new GenericException("You are not allowed to modify this resource");
        }
    }
}
