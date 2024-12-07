package fr.ishtamar.frozen.model.batch;

import fr.ishtamar.starter.util.EntityMapper;
import org.springframework.stereotype.Service;

@Service
public interface BatchService extends EntityMapper<BatchDto,Batch> {
}
