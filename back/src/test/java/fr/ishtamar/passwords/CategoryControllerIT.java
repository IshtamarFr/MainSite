package fr.ishtamar.passwords;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.TestContent;
import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.passwords.model.category.CategoryRepository;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.model.user.UserInfoRepository;
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
    CategoryRepository repository;
    @Autowired
    UserInfoRepository userRepository;
    @Autowired
    JwtService jwtService;

    ObjectMapper mapper=new ObjectMapper();

    @BeforeEach
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
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        repository.save(tc.initialCategory);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

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
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        repository.save(tc.initialCategory);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

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
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        repository.save(tc.initialCategory);
        String jwt= jwtService.generateToken(tc.initialUser2.getEmail());

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
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        repository.save(tc.initialCategory);
        repository.save(tc.initialCategory2);
        repository.save(tc.initialCategory3);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(get("/gestmdp/category")
                .header("Authorization","Bearer "+jwt))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Loisirs")))
                .andExpect(content().string(containsString("Maison")))
                .andExpect(content().string(Matchers.not(containsString("\"user_id\":2,"))));
    }

    @Test
    @DisplayName("When I try to delete my category, it works")
    @WithMockUser(roles="USER")
    void deleteCategoryWorks() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        long id=repository.save(tc.initialCategory).getId();
        repository.save(tc.initialCategory2);
        repository.save(tc.initialCategory3);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

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
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        long id=repository.save(tc.initialCategory).getId();
        repository.save(tc.initialCategory2);
        repository.save(tc.initialCategory3);
        String jwt= jwtService.generateToken(tc.initialUser2.getEmail());

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
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        long id=repository.save(tc.initialCategory).getId();
        repository.save(tc.initialCategory2);
        repository.save(tc.initialCategory3);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(put("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Saucisson"))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findByNameAndUser("Saucisson",tc.initialUser).size()).isEqualTo(1);
        assertThat(repository.findByNameAndUser("Loisirs",tc.initialUser).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("When I try to update other's category, it is bad request")
    @WithMockUser(roles="USER")
    void modifyOthersCategoryThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        long id=repository.save(tc.initialCategory).getId();
        repository.save(tc.initialCategory2);
        repository.save(tc.initialCategory3);
        String jwt= jwtService.generateToken(tc.initialUser2.getEmail());

        //When
        mockMvc.perform(put("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Saucisson"))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findByNameAndUser("Saucisson",tc.initialUser).size()).isEqualTo(0);
        assertThat(repository.findByNameAndUser("Loisirs",tc.initialUser).size()).isEqualTo(1);
        assertThat(repository.findByNameAndUser("Saucisson",tc.initialUser2).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("When I try to update my category with name already taken, it is bad request")
    @WithMockUser(roles="USER")
    void modifyCategoryWithTakenNameThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        long id=repository.save(tc.initialCategory).getId();
        repository.save(tc.initialCategory2);
        repository.save(tc.initialCategory3);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(put("/gestmdp/category/"+id)
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Maison"))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findByNameAndUser("Maison",tc.initialUser).size()).isEqualTo(1);
        assertThat(repository.findByNameAndUser("Loisirs",tc.initialUser).size()).isEqualTo(1);
    }
}
