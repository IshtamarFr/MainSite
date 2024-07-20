package fr.ishtamar.passwords;

import fr.ishtamar.passwords.model.password.Password;
import fr.ishtamar.passwords.model.password.PasswordService;
import fr.ishtamar.passwords.model.password.PasswordServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PasswordServiceImplTest {
    @Autowired
    PasswordService passwordService;

    @Test
    @DisplayName("When I calculate password, it works as expected")
    void calculatePasswordWorks() throws Exception {
        //Given
        Password password= Password.builder()
                .id(1L)
                .siteName("test")
                .siteAddress("test@test.com")
                .siteLogin("Pikachu")
                .passwordKey("z4-=%6H2Uzz^HT0]VX0jv9bzUE4lWEG8M??A|20r9M(%AuP<8}[nO(VrzT|A1>0?")
                .passwordPrefix("c7_P")
                .passwordLength(64L)
                .build();

        String secretKey="ca77e9310db4628bc3eaafaa62b93ca763ba2091b5f712d0d39f35ae7aee02";

        //When
        String realPassword=passwordService.calculatePassword(password,secretKey);

        //Then
        assertThat(realPassword)
                .isEqualTo("c7_Pos3/LwR9bak54IgxkBF1aCV7H/2zI25Q8AEaJW0qk3cva43sue3C/LeRb6JN");
    }

    @Test
    @Disabled
    void manualTestForGeneratePassword() {
        System.out.println(PasswordServiceImpl.generatePassword());
        System.out.println(PasswordServiceImpl.generatePrefix());
    }
}
