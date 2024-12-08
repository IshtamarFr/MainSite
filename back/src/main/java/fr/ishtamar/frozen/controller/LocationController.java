package fr.ishtamar.frozen.controller;

import fr.ishtamar.frozen.model.location.Location;
import fr.ishtamar.frozen.model.location.LocationDto;
import fr.ishtamar.frozen.model.location.LocationMapper;
import fr.ishtamar.frozen.model.location.LocationService;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.model.user.UserInfoService;
import fr.ishtamar.starter.security.JwtService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/frozen/location")
public class LocationController {
    private final LocationMapper locationMapper;
    private final JwtService jwtService;
    private final UserInfoService userInfoService;
    private final LocationService locationService;

    public LocationController(LocationMapper locationMapper, JwtService jwtService, UserInfoService userInfoService, LocationService locationService) {
        this.locationMapper = locationMapper;
        this.jwtService = jwtService;
        this.userInfoService = userInfoService;
        this.locationService = locationService;
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public LocationDto createLocation(
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestParam @NotNull @Size(max=63) String name,
            @RequestParam(required=false) @Size(max=128) String description) throws EntityNotFoundException {
        UserInfo user=userInfoService.getUserByUsername(jwtService.extractUsername(jwt.substring(7)));
        Location location=Location.builder()
                .name(name)
                .user(user)
                .description(description)
                .build();

        return locationMapper.toDto(locationService.createLocation(location));
    }
}
