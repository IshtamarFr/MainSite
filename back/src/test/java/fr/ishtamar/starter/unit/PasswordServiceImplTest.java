package fr.ishtamar.starter.unit;

import fr.ishtamar.starter.category.Category;
import fr.ishtamar.starter.password.Password;
import fr.ishtamar.starter.password.PasswordServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordServiceImplTest {
    @Autowired
    PasswordServiceImpl passwordService;

    @Test
    @DisplayName("When I calculate password, it works as expected")
    void calculatePasswordWorks() throws Exception {
        //Given
        Password password= Password.builder()
                .id(1L)
                .siteName("test")
                .siteAddress("test@test.com")
                .siteLogin("Pikachu")
                .category(new Category())
                .passwordKey("b1-C3WmbrPTNQXvGlBCEz3FJVjtxPzGIU1eE85Ph#pYkQdKeI-WJ_g*lVZ1S9I7aGZ7DQBbr9*IbjuZs73t@n3u2FY7TID2zlk*BcBfX8O7-jsOF5#gyAltkbqsrZp+e8ex*ZfGHw_svCN_Nm7LhaZh3ni0u4nJQTADuTixVVnEVVTU1jdgI1N#mfV2ELspRX2N66FhpGGfD")
                .passwordPrefix("c7_P")
                .passwordLength(31L)
                .build();

        //When
        String realPassword=passwordService.calculatePassword(password,"Aa123456");

        //Then
    }
}
