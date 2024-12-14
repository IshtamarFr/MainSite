package fr.ishtamar.frozen.controller;

import fr.ishtamar.frozen.model.dishtype.DishType;
import fr.ishtamar.frozen.model.dishtype.DishTypeDto;
import fr.ishtamar.frozen.model.dishtype.DishTypeMapper;
import fr.ishtamar.frozen.model.dishtype.DishTypeService;
import fr.ishtamar.frozen.model.location.Location;
import fr.ishtamar.frozen.model.location.LocationDto;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.security.JwtService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/frozen/dishtype")
public class DishTypeController {
    private final DishTypeMapper dishTypeMapper;
    private final JwtService jwtService;
    private final DishTypeService dishTypeService;

    public DishTypeController(DishTypeMapper dishTypeMapper, JwtService jwtService, DishTypeService dishTypeService) {
        this.dishTypeMapper = dishTypeMapper;
        this.jwtService = jwtService;
        this.dishTypeService = dishTypeService;
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public DishTypeDto createDishType(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestParam @NotNull @Size(max=32) String name,
            @RequestParam(required=false) @Min(0) Long monthsDefault) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        DishType dishType=DishType.builder()
                .name(name)
                .user(user)
                .build();

        dishType.setMonthsDefault(monthsDefault == null ? 0L : monthsDefault);
        return dishTypeMapper.toDto(dishTypeService.createEntity(dishType));
    }

    @GetMapping("")
    @Secured("ROLE_USER")
    public List<DishTypeDto> getDishTypesForUser(
            @RequestHeader(value="Authorization",required=false) String jwt) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        return dishTypeMapper.toDto(dishTypeService.getEntitiesForUser(user));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public String deleteDishType(@RequestHeader(value="Authorization",required=false) String jwt,@PathVariable Long id)
            throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        DishType dishType=dishTypeService.getEntityById(id);

        if (Objects.equals(dishType.getUser(),user)) {
            dishTypeService.deleteEntity(dishType);
            return "This dishType was successfully deleted";
        } else {
            throw new GenericException("You are not allowed to delete this resource");
        }
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public DishTypeDto modifyDishType(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable Long id,
            @RequestParam @NotNull @Size(max=32) String name,
            @RequestParam(required=false) @Min(0) Long monthsDefault
    ) throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        DishType dishType=dishTypeService.getEntityById(id);

        if (Objects.equals(dishType.getUser(),user)) {
            return dishTypeMapper.toDto(dishTypeService.modifyDishType(dishType,name,monthsDefault));
        } else {
            throw new GenericException("You are not allowed to modify this resource");
        }
    }
}
