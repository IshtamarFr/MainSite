package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends StdEntityServiceImpl<Location> implements LocationService {

    public LocationServiceImpl(StdEntityRepository<Location> repository) {
        super(repository);
    }

    @Override
    public Location modifyLocation(Location location, String name) throws GenericException {
        return null;
    }
}
