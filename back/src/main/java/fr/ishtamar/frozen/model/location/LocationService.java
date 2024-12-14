package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.standard.StdEntityService;

public interface LocationService extends StdEntityService<Location> {
    Location modifyLocation(Location location,String name,String description) throws GenericException;
}
