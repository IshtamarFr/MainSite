package fr.ishtamar.frozen;

import fr.ishtamar.TestContent;
import fr.ishtamar.frozen.model.location.LocationRepository;
import fr.ishtamar.starter.model.user.UserInfoRepository;
import fr.ishtamar.starter.security.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LocationRepository repository;
    @Autowired
    UserInfoRepository userRepository;
    @Autowired
    JwtService jwtService;

    @BeforeEach
    @AfterEach
    void clean(){
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("When I try to create a location, it works")
    void testCreateLocationWorks() throws Exception{
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(post("/frozen/location")
                .header("Authorization","Bearer "+jwt)
                .param("name","cuisine")
                .param("description","frigo principal"))
        //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When I try to create a location with no description, it works")
    void testCreateLocationWoDescriptionWorks() throws Exception{
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(post("/frozen/location")
                        .header("Authorization","Bearer "+jwt)
                        .param("name","cave"))
                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When I try to create a location with a name already taken, an error is thrown")
    void testCreateLocationExistingThrowsError() throws Exception{
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        repository.save(tc.location1);

        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(post("/frozen/location")
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Haut"))
                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When I try to create a location with a name already taken with another user, it is OK")
    void testCreateLocationExistingWorksWithOther() throws Exception{
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);
        repository.save(tc.location1);

        String jwt= jwtService.generateToken(tc.initialUser2.getEmail());

        //When
        mockMvc.perform(post("/frozen/location")
                        .header("Authorization","Bearer "+jwt)
                        .param("name","Haut"))
                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("When I try to get all my locations, it works")
    void testGetAllLocationsForMe() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);
        repository.save(tc.location1);
        repository.save(tc.location2);
        repository.save(tc.location3);

        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(get("/frozen/location")
                .header("Authorization","Bearer "+jwt))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)));

    }

    @Test
    @DisplayName("When I try to delete my location, it works")
    void testDeleteMyLocationWorks() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        Long id=repository.save(tc.location1).getId();

        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(delete("/frozen/location/"+id)
                        .header("Authorization","Bearer "+jwt))

                //Then
                .andExpect(status().isOk());

        assertThat(repository.findAll().size()).isEqualTo(0L);
    }

    @Test
    @DisplayName("When I try to delete inexistant location, it is not found")
    void testDeleteInexistantLocationThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        Long id=repository.save(tc.location1).getId()+1000;

        String jwt= jwtService.generateToken(tc.initialUser.getEmail());

        //When
        mockMvc.perform(delete("/frozen/location/"+id)
                        .header("Authorization","Bearer "+jwt))

                //Then
                .andExpect(status().isNotFound());

        assertThat(repository.findAll().size()).isEqualTo(1L);
    }

    @Test
    @DisplayName("When I try to delete other's location, it is bad request")
    void testDeleteOthersLocationThrowsError() throws Exception {
        //Given
        TestContent tc=new TestContent();
        userRepository.save(tc.initialUser);
        userRepository.save(tc.initialUser2);
        Long id=repository.save(tc.location1).getId();

        String jwt= jwtService.generateToken(tc.initialUser2.getEmail());

        //When
        mockMvc.perform(delete("/frozen/location/"+id)
                        .header("Authorization","Bearer "+jwt))

                //Then
                .andExpect(status().isBadRequest());

        assertThat(repository.findAll().size()).isEqualTo(1L);
    }
}
