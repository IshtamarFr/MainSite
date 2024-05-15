package fr.ishtamar.starter.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.starter.category.Category;
import fr.ishtamar.starter.category.CategoryRepository;
import fr.ishtamar.starter.category.CategoryServiceImpl;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.user.UserInfo;
import fr.ishtamar.starter.user.UserInfoRepository;
import org.hamcrest.Matchers;
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
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIT {
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

    final Category initialCategory2=Category.builder()
            .name("Maison")
            .user(initialUser)
            .build();

    final Category initialCategory3=Category.builder()
            .name("Jeux")
            .user(initialUser2)
            .build();

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        repository.deleteAll();
        userRepository.save(initialUser);
        userRepository.save(initialUser2);
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
        repository.save(initialCategory);
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(post("/gestmdp/category")
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
        repository.save(initialCategory);
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(post("/gestmdp/category")
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
        repository.save(initialCategory);
        String jwt= jwtService.generateToken(initialUser2.getEmail());

        //When
        mockMvc.perform(post("/gestmdp/category")
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Loisirs"))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("When I try to get all categories by user, it works")
    @WithMockUser(roles="USER")
    void getAllCategoriesByUserWorks() throws Exception {
        //Given
        repository.save(initialCategory);
        repository.save(initialCategory2);
        repository.save(initialCategory3);
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(get("/gestmdp/category")
                .header("Authorization","Bearer "+jwt))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Loisirs")))
                .andExpect(content().string(containsString("Maison")))
                .andExpect(content().string(Matchers.not(containsString("\"user_id\":2"))));
    }

    @Test
    @DisplayName("When I try to delete my category, it works")
    @WithMockUser(roles="USER")
    void deleteCategoryWorks() throws Exception {
        //Given
        long id=repository.save(initialCategory).getId();
        repository.save(initialCategory2);
        repository.save(initialCategory3);
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(delete("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("When I try to delete other's category, it is bad request")
    @WithMockUser(roles="USER")
    void deleteOthersCategoryThrowsError() throws Exception {
        //Given
        long id=repository.save(initialCategory).getId();
        repository.save(initialCategory2);
        repository.save(initialCategory3);
        String jwt= jwtService.generateToken(initialUser2.getEmail());

        //When
        mockMvc.perform(delete("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("When I try to update my category, it works")
    @WithMockUser(roles="USER")
    void modifyCategoryWorks() throws Exception {
        //Given
        long id=repository.save(initialCategory).getId();
        repository.save(initialCategory2);
        repository.save(initialCategory3);
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(put("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Saucisson"))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findByNameAndUser("Saucisson",initialUser).size()).isEqualTo(1);
        assertThat(repository.findByNameAndUser("Loisirs",initialUser).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("When I try to update other's category, it is bad request")
    @WithMockUser(roles="USER")
    void modifyOthersCategoryThrowsError() throws Exception {
        //Given
        long id=repository.save(initialCategory).getId();
        repository.save(initialCategory2);
        repository.save(initialCategory3);
        String jwt= jwtService.generateToken(initialUser2.getEmail());

        //When
        mockMvc.perform(put("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Saucisson"))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findByNameAndUser("Saucisson",initialUser).size()).isEqualTo(0);
        assertThat(repository.findByNameAndUser("Loisirs",initialUser).size()).isEqualTo(1);
        assertThat(repository.findByNameAndUser("Saucisson",initialUser2).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("When I try to update my category with name already taken, it is bad request")
    @WithMockUser(roles="USER")
    void modifyCategoryWithTakenNameThrowsError() throws Exception {
        //Given
        long id=repository.save(initialCategory).getId();
        repository.save(initialCategory2);
        repository.save(initialCategory3);
        String jwt= jwtService.generateToken(initialUser.getEmail());

        //When
        mockMvc.perform(put("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Maison"))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findByNameAndUser("Maison",initialUser).size()).isEqualTo(1);
        assertThat(repository.findByNameAndUser("Loisirs",initialUser).size()).isEqualTo(1);
    }
}
