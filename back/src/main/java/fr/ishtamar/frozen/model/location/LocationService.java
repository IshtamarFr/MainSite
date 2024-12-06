package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;

import java.util.List;

public interface LocationService {
    Location createLocation(Location location) throws GenericException;
    List<Location> getLocationsForUser(UserInfo user);
    Location getLocationById(Long id) throws EntityNotFoundException;
    void deleteLocation(Location location);
    Location modifyLocation(Location location,String name) throws GenericException;
}
