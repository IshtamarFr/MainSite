package fr.ishtamar.frozen.controller;

import fr.ishtamar.frozen.model.location.Location;
import fr.ishtamar.frozen.model.location.LocationDto;
import fr.ishtamar.frozen.model.location.LocationMapper;
import fr.ishtamar.frozen.model.location.LocationService;
import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.passwords.model.category.CategoryDto;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.security.JwtService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/frozen/location")
public class LocationController {
    private final LocationMapper locationMapper;
    private final JwtService jwtService;
    private final LocationService locationService;

    public LocationController(LocationMapper locationMapper, JwtService jwtService, LocationService locationService) {
        this.locationMapper = locationMapper;
        this.jwtService = jwtService;
        this.locationService = locationService;
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public LocationDto createLocation(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestParam @NotNull @Size(max=32) String name,
            @RequestParam(required=false) @Size(max=128) String description) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        Location location=Location.builder()
                .name(name)
                .user(user)
                .description(description)
                .build();

        return locationMapper.toDto(locationService.createEntity(location));
    }

    @GetMapping("")
    @Secured("ROLE_USER")
    public List<LocationDto> getLocationsForUser(
            @RequestHeader(value="Authorization",required=false) String jwt) throws EntityNotFoundException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        return locationMapper.toDto(locationService.getEntitiesForUser(user));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public String deleteLocation(@RequestHeader(value="Authorization",required=false) String jwt,@PathVariable Long id)
            throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        Location location=locationService.getEntityById(id);

        if (Objects.equals(location.getUser(),user)) {
            locationService.deleteEntity(location);
            return "This location was successfully deleted";
        } else {
            throw new GenericException("You are not allowed to delete this resource");
        }
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public LocationDto modifyLocation(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @PathVariable Long id,
            @RequestParam @NotNull @Size(max=32) String name,
            @RequestParam(required=false) @Size(max=128) String description
    ) throws EntityNotFoundException, GenericException {
        UserInfo user=jwtService.getUserFromJwt(jwt);
        Location location=locationService.getEntityById(id);

        if (Objects.equals(location.getUser(),user)) {
            return locationMapper.toDto(locationService.modifyLocation(location,name,description));
        } else {
            throw new GenericException("You are not allowed to modify this resource");
        }
    }
}
