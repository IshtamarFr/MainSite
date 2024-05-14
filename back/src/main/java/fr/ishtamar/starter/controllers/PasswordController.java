package fr.ishtamar.starter.controllers;

import fr.ishtamar.starter.category.CategoryService;
import fr.ishtamar.starter.category.CategoryServiceImpl;
import fr.ishtamar.starter.password.PasswordDto;
import fr.ishtamar.starter.password.PasswordMapper;
import fr.ishtamar.starter.password.PasswordService;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.user.UserInfoService;
import fr.ishtamar.starter.user.UserInfoServiceImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/password/category/{id}/password")
    @Secured("ROLE_USER")
    public PasswordDto createPassword() {
        return null;
    }
}
