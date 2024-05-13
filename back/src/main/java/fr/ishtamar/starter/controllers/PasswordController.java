package fr.ishtamar.starter.controllers;

import fr.ishtamar.starter.category.*;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.user.UserInfo;
import fr.ishtamar.starter.user.UserInfoService;
import fr.ishtamar.starter.user.UserInfoServiceImpl;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller deals with both Password and Category, which are deeply linked
 */
@RestController
public class PasswordController {
    private final JwtService jwtService;
    private final UserInfoService userInfoService;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public PasswordController(
            JwtService jwtService,
            UserInfoServiceImpl userInfoService,
            CategoryMapper categoryMapper,
            CategoryServiceImpl categoryService
    ){
        this.jwtService=jwtService;
        this.userInfoService=userInfoService;
        this.categoryMapper=categoryMapper;
        this.categoryService=categoryService;
    }

    @PostMapping("/password/category")
    @Secured("ROLE_USER")
    public CategoryDto createCategory(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestParam @NotNull @Size(max=63) String name) throws EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Category category= Category.builder()
                .name(name)
                .user(user)
                .build();

        return categoryMapper.toDto(categoryService.createCategory(category));
    }
}
