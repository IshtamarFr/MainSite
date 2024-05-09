package fr.ishtamar.starter.password;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository repository;

    public PasswordServiceImpl(PasswordRepository repository) {
        this.repository = repository;
    }

    public List<Password> getAllTrucs() {
        return repository.findAll();
    }

    @Override
    public Password getTrucById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Password.class,"id",id.toString()));
    }
}
