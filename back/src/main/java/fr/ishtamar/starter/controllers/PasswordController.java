package fr.ishtamar.starter.controllers;

import fr.ishtamar.starter.category.Category;
import fr.ishtamar.starter.category.CategoryService;
import fr.ishtamar.starter.category.CategoryServiceImpl;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.password.CreatePasswordRequest;
import fr.ishtamar.starter.password.PasswordDto;
import fr.ishtamar.starter.password.PasswordMapper;
import fr.ishtamar.starter.password.PasswordService;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.user.UserInfo;
import fr.ishtamar.starter.user.UserInfoService;
import fr.ishtamar.starter.user.UserInfoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class PasswordController {
    private final JwtService jwtService;
    private final UserInfoService userInfoService;
    private final PasswordMapper passwordMapper;
    private final PasswordService passwordService;
    private final CategoryService categoryService;

    public PasswordController(
            JwtService jwtService,
            UserInfoServiceImpl userInfoService,
            PasswordMapper passwordMapper,
            PasswordService passwordService,
            CategoryServiceImpl categoryService
    ){
        this.jwtService=jwtService;
        this.userInfoService=userInfoService;
        this.passwordMapper = passwordMapper;
        this.passwordService=passwordService;
        this.categoryService=categoryService;
    }

    @PostMapping("/gestmdp/category/{id}/password")
    @Secured("ROLE_USER")
    public PasswordDto createPassword(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id,
            @RequestBody @Valid CreatePasswordRequest request
    ) throws GenericException, EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Category category=categoryService.getCategoryById(id);

        if (Objects.equals(category.getUser(),user)) {
            return null;
        }else {
            throw new GenericException("You are not allowed to add a password for this category");
        }
    }
}
