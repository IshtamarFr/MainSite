package fr.ishtamar.frozen.model.containertype;

import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerTypeServiceImpl extends StdEntityServiceImpl<ContainerType> implements ContainerTypeService {
    public ContainerTypeServiceImpl(StdEntityRepository<ContainerType> repository) {
        super(repository);
    }

    @Override
    public ContainerType modifyContainerType(ContainerType containerType, String name) {
        List<ContainerType> candidate=repository.findByNameAndUser(name,containerType.getUser());

        if (candidate.isEmpty()) {
            containerType.setName(name);
            return repository.save(containerType);
        } else {
            throw new GenericException("This category already exists for this user");
        }
    }
}
