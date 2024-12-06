package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location createLocation(Location location) throws GenericException {
        return null;
    }

    @Override
    public List<Location> getLocationsForUser(UserInfo user) {
        return List.of();
    }

    @Override
    public Location getLocationById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void deleteLocation(Location location) {

    }

    @Override
    public Location modifyLocation(Location location, String name) throws GenericException {
        return null;
    }
}
