package fr.ishtamar;

import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.passwords.model.password.Password;
import fr.ishtamar.starter.model.user.UserInfo;

import static fr.ishtamar.starter.security.SecurityConfig.passwordEncoder;

public class TestContent {
    public UserInfo initialUser=UserInfo.builder()
            .name("Ishta")
            .email("test@test.com")
            .password(passwordEncoder().encode("123456"))
            .roles("ROLE_USER")
            .build();

    public UserInfo initialUser2=UserInfo.builder()
            .name("Pal")
            .email("test17@test.com")
            .password(passwordEncoder().encode("654321"))
            .roles("ROLE_USER")
            .build();

    public Category initialCategory=Category.builder()
            .name("Loisirs")
            .user(initialUser)
            .build();

    public Category initialCategory2=Category.builder()
            .name("Maison")
            .user(initialUser)
            .build();

    public Category initialCategory3=Category.builder()
            .name("Jeux")
            .user(initialUser2)
            .build();

    public Password initialPassword= Password.builder()
            .passwordKey("z4-=%6H2Uzz^HT0]VX0jv9bzUE4lWEG8M??A|20r9M(%AuP<8}[nO(VrzT|A1>0?")
            .passwordPrefix("c7_P")
            .passwordLength(64L)
            .siteName("saumon@test.te")
            .category(initialCategory)
            .active(true)
            .build();

    public Password initialPassword2= Password.builder()
            .passwordKey("abcdefgh")
            .passwordPrefix("a1_A")
            .passwordLength(64L)
            .siteName("truite@test.te")
            .category(initialCategory)
            .active(true)
            .build();

    public Password initialPassword3= Password.builder()
            .passwordKey("abcdefgh")
            .passwordPrefix("a1_A")
            .passwordLength(64L)
            .siteName("cabillaud@test.te")
            .category(initialCategory3)
            .active(true)
            .build();

    public Password initialPassword4= Password.builder()
            .passwordKey("abcdefgh")
            .passwordPrefix("a1_A")
            .passwordLength(64L)
            .siteName("thon@test.te")
            .category(initialCategory)
            .active(true)
            .build();
}
