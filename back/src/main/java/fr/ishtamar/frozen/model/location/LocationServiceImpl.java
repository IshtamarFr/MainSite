package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl extends StdEntityServiceImpl<Location> implements LocationService {

    public LocationServiceImpl(StdEntityRepository<Location> repository) {
        super(repository);
    }

    @Override
    public Location modifyLocation(Location location, String name, String description) throws GenericException {
        List<Location> candidate=repository.findByNameAndUser(name,location.getUser());

        if (candidate.isEmpty()) {
            location.setName(name);
            location.setDescription(description);
            return repository.save(location);
        } else {
            throw new GenericException("This location already exists for this user");
        }
    }
}
