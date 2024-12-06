package fr.ishtamar.passwords;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.TestContent;
import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.passwords.model.category.CategoryRepository;
import fr.ishtamar.passwords.model.category.CategoryService;
import fr.ishtamar.passwords.model.category.CategoryServiceImpl;
import fr.ishtamar.passwords.model.password.*;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.model.user.UserInfoRepository;
import fr.ishtamar.starter.security.JwtService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static fr.ishtamar.starter.security.SecurityConfig.passwordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PasswordRepository repository;
    @Autowired
    UserInfoRepository userRepository;
    @Autowired
    JwtService jwtService;

    ObjectMapper mapper=new ObjectMapper();

    @BeforeEach
    @AfterEach
    void clean() {
        repository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("When I try to add a password to category, it works")
    @Secured("USER")
    void testAddPasswordWorks() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        Long id=categoryRepository.save(tc.initialCategory).getId();
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        CreatePasswordRequest request= CreatePasswordRequest.builder()
                .siteName("Site de test")
                .siteAddress("https://test.testing.te")
                .build();

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/gestmdp/category/"+id+"/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .content(mapper.writeValueAsString(request)))

        //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When I try to add a password to other's category, it is bad request")
    @Secured("USER")
    void testAddPasswordToOtherThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        Long id=categoryRepository.save(tc.initialCategory).getId();
        String jwt= jwtService.generateToken(tc.initialUser2.getEmail());

        CreatePasswordRequest request= CreatePasswordRequest.builder()
                .siteName("Site de test")
                .siteAddress("https://test.testing.te")
                .build();

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/gestmdp/category/"+id+"/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+jwt)
                        .content(mapper.writeValueAsString(request)))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("When I try to get all my passwords, it works")
    @Secured("USER")
    void testGetAllPasswordsWorks() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        categoryRepository.save(tc.initialCategory);
        categoryRepository.save(tc.initialCategory2);
        categoryRepository.save(tc.initialCategory3);

        repository.save(tc.initialPassword);
        repository.save(tc.initialPassword2);
        repository.save(tc.initialPassword3);
        repository.save(tc.initialPassword4);

        String jwt = jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/gestmdp/password")
                        .header("Authorization", "Bearer " + jwt))

                //Then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("saumon")))
                .andExpect(content().string(containsString("thon")))
                .andExpect(content().string(containsString("truite")))
                .andExpect(content().string(Matchers.not(containsString("cabillaud"))));
    }

    @Test
    @DisplayName("When I try to calculate my password, it works")
    @WithMockUser(roles="USER")
    void testCalculatePasswordWorks() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        categoryRepository.save(tc.initialCategory);
        Long id=repository.save(tc.initialPassword).getId();
        repository.save(tc.initialPassword2);
        String jwt = jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/gestmdp/password/"+id)
                .header("Authorization", "Bearer " + jwt)
                        .content("ca77e9310db4628bc3eaafaa62b93ca763ba2091b5f712d0d39f35ae7aee02"))

                //Then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "c7_Pos3/LwR9bak54IgxkBF1aCV7H/2zI25Q8AEaJW0qk3cva43sue3C/LeRb6JN")));
    }

    @Test
    @DisplayName("When I try to calculate other's password, it is bad request")
    @WithMockUser(roles="USER")
    void testCalculateOthersPasswordThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        categoryRepository.save(tc.initialCategory);
        Long id=repository.save(tc.initialPassword).getId();
        repository.save(tc.initialPassword2);
        String jwt = jwtService.generateToken(tc.initialUser2.getEmail());

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/gestmdp/password/"+id)
                        .header("Authorization", "Bearer " + jwt)
                        .content("ca77e9310db4628bc3eaafaa62b93ca763ba2091b5f712d0d39f35ae7aee02"))

                //Then
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When I try to modify a password, it works")
    @WithMockUser(roles="USER")
    void testModifyMyPasswordWorks() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        Long categoryId = categoryRepository.save(tc.initialCategory).getId();
        Long id = repository.save(tc.initialPassword).getId();
        String jwt = jwtService.generateToken(tc.initialUser.getEmail());

        CreatePasswordRequest request = CreatePasswordRequest.builder()
                .siteName("Site de test")
                .siteAddress("https://test.testing.te")
                .build();

        assertThat(repository.findAll().toString()).contains("saumon@test.te");

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/gestmdp/password/" + id)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .param("category_id", categoryId.toString()))

                //Then
                .andExpect(status().isOk());

        List<Password> passwords = repository.findAll();
        assertThat(passwords.size()).isEqualTo(1);
        assertThat(passwords.toString()).contains("test.testing.te");
        assertThat(passwords.toString()).doesNotContain("saumon@test.te");
    }

    @Test
    @DisplayName("When I try to modify another's password, it is bad request")
    @WithMockUser(roles="USER")
    void testModifyOthersPasswordThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        Long categoryId = categoryRepository.save(tc.initialCategory).getId();
        Long id = repository.save(tc.initialPassword).getId();
        String jwt = jwtService.generateToken(tc.initialUser2.getEmail());

        CreatePasswordRequest request = CreatePasswordRequest.builder()
                .siteName("Site de test")
                .siteAddress("https://test.testing.te")
                .build();

        assertThat(repository.findAll().toString()).contains("saumon@test.te");

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/gestmdp/password/" + id)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .param("category_id", categoryId.toString()))

                //Then
                .andExpect(status().isBadRequest());

        List<Password> passwords = repository.findAll();
        assertThat(passwords.size()).isEqualTo(1);
        assertThat(passwords.toString()).contains("saumon@test.te");
        assertThat(passwords.toString()).doesNotContain("test@testing.te");
    }

    @Test
    @DisplayName("When I try to modify my password to match someone else's category, it is bad request")
    @WithMockUser(roles="USER")
    void testModifyMyPasswordWithOthersCategoryThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);

        categoryRepository.save(tc.initialCategory);
        Long categoryId = categoryRepository.save(tc.initialCategory3).getId();
        Long id = repository.save(tc.initialPassword).getId();
        String jwt = jwtService.generateToken(tc.initialUser.getEmail());

        CreatePasswordRequest request = CreatePasswordRequest.builder()
                .siteName("Site de test")
                .siteAddress("https://test.testing.te")
                .build();

        assertThat(repository.findAll().toString()).contains("saumon@test.te");

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/gestmdp/password/" + id)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .param("category_id", categoryId.toString()))

                //Then
                .andExpect(status().isBadRequest());

        List<Password> passwords = repository.findAll();
        assertThat(passwords.size()).isEqualTo(1);
        assertThat(passwords.toString()).contains("saumon@test.te");
        assertThat(passwords.toString()).doesNotContain("test@testing.te");
    }
}
