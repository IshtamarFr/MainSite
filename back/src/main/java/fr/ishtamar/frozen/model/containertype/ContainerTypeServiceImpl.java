package fr.ishtamar.frozen.model.containertype;

import fr.ishtamar.starter.standard.StdEntityRepository;
import fr.ishtamar.starter.standard.StdEntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ContainerTypeServiceImpl extends StdEntityServiceImpl<ContainerType> implements ContainerTypeService {
    public ContainerTypeServiceImpl(StdEntityRepository<ContainerType> repository) {
        super(repository);
    }
}
