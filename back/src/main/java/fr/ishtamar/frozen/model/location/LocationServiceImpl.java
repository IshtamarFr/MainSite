package fr.ishtamar.frozen.model.location;

import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.repository = locationRepository;
    }

    @Override
    public Location createLocation(Location location) throws GenericException {
        List<Location> candidate=repository.findByNameAndUser(location.getName(),location.getUser());

        if (candidate.isEmpty()) {
            return repository.save(location);
        } else {
            throw new GenericException("This category already exists for this user");
        }
    }

    @Override
    public List<Location> getLocationsForUser(UserInfo user) {
        return repository.findByUser(user);
    }

    @Override
    public Location getLocationById(Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Location.class,"id",id.toString()));
    }

    @Override
    public void deleteLocation(Location location) {
        repository.delete(location);
    }

    @Override
    public Location modifyLocation(Location location, String name) throws GenericException {
        List<Location> candidate=repository.findByNameAndUser(name,location.getUser());

        if (candidate.isEmpty()) {
            location.setName(name);
            return repository.save(location);
        } else {
            throw new GenericException("This category already exists for this user");
        }
    }
}
