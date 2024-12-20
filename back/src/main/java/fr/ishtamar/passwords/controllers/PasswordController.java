package fr.ishtamar.passwords.controllers;

import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.passwords.model.category.CategoryService;
import fr.ishtamar.passwords.model.category.CategoryServiceImpl;
import fr.ishtamar.passwords.model.password.*;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.model.user.UserInfoService;
import fr.ishtamar.starter.model.user.UserInfoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/gestmdp")
public class PasswordController {
    private final JwtService jwtService;
    private final UserInfoService userInfoService;
    private final PasswordMapper passwordMapper;
    private final PasswordService passwordService;
    private final CategoryService categoryService;

    public PasswordController(
            JwtService jwtService,
            UserInfoService userInfoService,
            PasswordMapper passwordMapper,
            PasswordService passwordService,
            CategoryService categoryService
    ){
        this.jwtService=jwtService;
        this.userInfoService=userInfoService;
        this.passwordMapper = passwordMapper;
        this.passwordService=passwordService;
        this.categoryService=categoryService;
    }

    @PostMapping("/category/{id}/password")
    @Secured("ROLE_USER")
    public PasswordDto createPassword(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id,
            @RequestBody @Valid CreatePasswordRequest request
    ) throws GenericException, EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Category category=categoryService.getEntityById(id);

        if (Objects.equals(category.getUser(),user)) {
            return passwordMapper.toDto(passwordService.createPassword(request,user,category));
        }else {
            throw new GenericException("You are not allowed to add a password for this category");
        }
    }

    @GetMapping("/password")
    @Secured("ROLE_USER")
    public List<PasswordDto> getPasswords(
            @RequestHeader(value="Authorization",required=false) String jwt
    ) throws EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        return passwordMapper.toDto(passwordService.getPasswordsByUser(user));
    }

    @GetMapping("/password/{id}")
    @Secured("ROLE_USER")
    public PasswordDto getPasswordById(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id
    ) throws EntityNotFoundException, GenericException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Password password=passwordService.getPasswordById(id);

        if (Objects.equals(password.getCategory().getUser(),user)) {
            return passwordMapper.toDto(password);
        } else {
            throw new GenericException("You are not allowed to get this password");
        }
    }

    @PostMapping("/password/{id}")
    @Secured("ROLE_USER")
    public String calculatePassword(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id,
            @RequestBody String secretKey
    ) throws EntityNotFoundException, GenericException, NoSuchAlgorithmException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Password password=passwordService.getPasswordById(id);

        if (Objects.equals(password.getCategory().getUser(),user)) {
            return passwordService.calculatePassword(password,secretKey);
        } else {
            throw new GenericException("You are not allowed to require this real password");
        }
    }

    @DeleteMapping("/password/{id}")
    @Secured("ROLE_USER")
    public String deletePassword(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id
    ) throws EntityNotFoundException, GenericException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Password password=passwordService.getPasswordById(id);

        if (Objects.equals(password.getCategory().getUser(),user)) {
            passwordService.deletePassword(password);
            return "This password was successfully deleted";
        } else {
            throw new GenericException("You are not allowed to delete this password");
        }
    }

    @PutMapping("/password/{id}")
    @Secured("ROLE_USER")
    public PasswordDto modifyPassword(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id,
            @RequestBody @Valid CreatePasswordRequest request,
            @RequestParam Long category_id
    ) throws GenericException, EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Category category=categoryService.getEntityById(category_id);
        Password password=passwordService.getPasswordById(id);

        if (Objects.equals(password.getCategory().getUser(),user) && Objects.equals(category.getUser(),user)) {
            return passwordMapper.toDto(passwordService.modifyPassword(password, request, category));
        }else {
            throw new GenericException("You are not allowed to modify this password");
        }
    }

    @PutMapping("/password/{id}/status")
    @Secured("ROLE_USER")
    public PasswordDto togglePasswordStatus(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable final Long id,
            @RequestParam boolean status
    ) throws GenericException, EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Password password=passwordService.getPasswordById(id);

        if (Objects.equals(password.getCategory().getUser(),user)) {
            return passwordMapper.toDto(passwordService.togglePasswordStatus(password, status));
        }else {
            throw new GenericException("You are not allowed to modify this password");
        }
    }
}
