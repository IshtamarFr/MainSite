package fr.ishtamar;

import fr.ishtamar.frozen.model.batch.Batch;
import fr.ishtamar.frozen.model.containertype.ContainerType;
import fr.ishtamar.frozen.model.dishtype.DishType;
import fr.ishtamar.frozen.model.food.Food;
import fr.ishtamar.frozen.model.location.Location;
import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.passwords.model.password.Password;
import fr.ishtamar.starter.model.user.UserInfo;

import java.time.LocalDateTime;

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

    public Location location1=Location.builder()
            .name("Haut")
            .description("Congélateur de la cuisine")
            .user(initialUser)
            .build();

    public Location location2=Location.builder()
            .name("Haut")
            .description("Congélateur de la cuisine")
            .user(initialUser2)
            .build();

    public Location location3=Location.builder()
            .name("Salon")
            .description("Congélateur du salon")
            .user(initialUser)
            .build();

    public DishType dishtype1=DishType.builder()
            .name("Boeuf cuit")
            .user(initialUser)
            .build();

    public ContainerType container1=ContainerType.builder()
            .name("GN")
            .user(initialUser)
            .build();

    public Food food1= Food.builder()
            .name("Boeuf bourguignon")
            .createdAt(LocalDateTime.now())
            .dlc(LocalDateTime.now().plusMonths(6))
            .dishType(dishtype1)
            .user(initialUser)
            .build();

    public Batch batch1food1= Batch.builder()
            .units(2L)
            .location(location1)
            .containerType(container1)
            .food(food1)
            .user(initialUser)
            .build();
}
