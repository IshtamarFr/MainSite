package fr.ishtamar.starter.password;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
    public Password getPasswordById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Password.class,"id",id.toString()));
    }

    @Override
    public String calculatePassword(Password password, String secretKey) throws NoSuchAlgorithmException {
        return password.getPasswordPrefix()+
                sha256(secretKey+password.getPasswordKey()+password.getPasswordPrefix())
                        .substring(0,password.getPasswordLength().intValue());
    }

    private String sha256(String message) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(message.getBytes()));
    }
}
