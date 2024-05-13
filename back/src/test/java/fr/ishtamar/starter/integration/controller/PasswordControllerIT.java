package fr.ishtamar.starter.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.starter.category.Category;
import fr.ishtamar.starter.category.CategoryRepository;
import fr.ishtamar.starter.category.CategoryServiceImpl;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.user.UserInfo;
import fr.ishtamar.starter.user.UserInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static fr.ishtamar.starter.security.SecurityConfig.passwordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CategoryServiceImpl service;
    @Autowired
    CategoryRepository repository;
    @Autowired
    UserInfoRepository userRepository;
    @Autowired
    JwtService jwtService;

    ObjectMapper mapper=new ObjectMapper();
    final UserInfo initialUser=UserInfo.builder()
            .name("Ishta")
            .email("test@test.com")
            .password(passwordEncoder().encode("123456"))
            .roles("ROLE_USER")
            .build();

    final UserInfo initialUser2=UserInfo.builder()
            .name("Pal")
            .email("test17@test.com")
            .password(passwordEncoder().encode("654321"))
            .roles("ROLE_USER")
            .build();

    final Category initialCategory=Category.builder()
            .name("Loisirs")
            .user(initialUser)
            .build();

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        repository.deleteAll();
        userRepository.save(initialUser);
        userRepository.save(initialUser2);
        repository.save(initialCategory);
    }

    @AfterEach
    void clean() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("When I try to create a new category, it works")
    @WithMockUser(roles="USER")
    void createNewCategoryWorks() throws Exception {
        //Given
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(post("/password/category")
                .header("Authorization","Bearer "+jwt)
                        .param("name","Administratif"))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("When I try to create a new category with same name, an error is thrown")
    @WithMockUser(roles="USER")
    void createNewCategoryWithAlreadyUsedName() throws Exception {
        //Given
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(post("/password/category")
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Loisirs"))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When I try to create a new category, with name already taken by other user, it works")
    @WithMockUser(roles="USER")
    void createNewCategoryWorksWithSameNameFromOtherUser() throws Exception {
        //Given
        String jwt= jwtService.generateToken(initialUser2.getEmail());

        //When
        mockMvc.perform(post("/password/category")
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Loisirs"))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(2);
    }
}
