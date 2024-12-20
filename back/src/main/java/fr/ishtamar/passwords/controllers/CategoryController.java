package fr.ishtamar.passwords.controllers;

import fr.ishtamar.passwords.model.category.*;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.model.user.UserInfoService;
import fr.ishtamar.starter.model.user.UserInfoServiceImpl;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/gestmdp/category")
public class CategoryController {
    private final JwtService jwtService;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public CategoryController(
            JwtService jwtService,
            CategoryMapper categoryMapper,
            CategoryService categoryService
    ){
        this.jwtService=jwtService;
        this.categoryMapper=categoryMapper;
        this.categoryService=categoryService;
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public CategoryDto createCategory(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestParam @NotNull @Size(max=63) String name) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        Category category= Category.builder()
                .name(name)
                .user(user)
                .build();

        return categoryMapper.toDto(categoryService.createEntity(category));
    }

    @GetMapping("")
    @Secured("ROLE_USER")
    public List<CategoryDto> getCategoriesForUser(
            @RequestHeader(value="Authorization",required=false) String jwt) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        return categoryMapper.toDto(categoryService.getEntitiesForUser(user));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public String deleteCategory(@RequestHeader(value="Authorization",required=false) String jwt,@PathVariable Long id)
            throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        Category category=categoryService.getEntityById(id);

        if (Objects.equals(category.getUser(),user)) {
            categoryService.deleteEntity(category);
            return "This category was successfully deleted";
        } else {
            throw new GenericException("You are not allowed to delete this resource");
        }
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public CategoryDto modifyCategory(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable Long id,
            @RequestParam @NotNull @Size(max=63) String name
    ) throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        Category category=categoryService.getEntityById(id);

        if (Objects.equals(category.getUser(),user)) {
            return categoryMapper.toDto(categoryService.modifyCategory(category,name));
        } else {
            throw new GenericException("You are not allowed to modify this resource");
        }
    }
}
